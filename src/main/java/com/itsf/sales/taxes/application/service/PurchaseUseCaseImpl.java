package com.itsf.sales.taxes.application.service;

import com.itsf.sales.taxes.domain.model.OrderLine;
import com.itsf.sales.taxes.domain.model.OrderLineIdModel;
import com.itsf.sales.taxes.domain.model.Price;
import com.itsf.sales.taxes.domain.model.Product;
import com.itsf.sales.taxes.domain.model.Purchase;
import com.itsf.sales.taxes.domain.model.Quantity;
import com.itsf.sales.taxes.domain.model.TotalTax;
import com.itsf.sales.taxes.domain.port.in.PurchaseUseCase;
import com.itsf.sales.taxes.domain.port.out.OrderLinePort;
import com.itsf.sales.taxes.domain.port.out.ProductPort;
import com.itsf.sales.taxes.domain.port.out.PurchasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseUseCaseImpl implements PurchaseUseCase {

  private final PurchasePort purchasePort;
  private final ProductPort productPort;
  private final OrderLinePort orderLinePort;
  private final PriceTtcCalculator priceTtcCalculator;

  private static BigDecimal subtractNonNegative(BigDecimal base, BigDecimal decrement) {
    BigDecimal result = base.subtract(decrement);
    return (result.signum() < 0) ? BigDecimal.ZERO : result;
  }

  @Override
  public Purchase getPurchase(final long idPurchase) {
    List<Product> productsWithPriceTtc = productPort.findProductByIdPurchase(idPurchase)
      .stream()
      .map(priceTtcCalculator::calculer)
      .toList();
    Purchase purchase = purchasePort.findPurchaseById(idPurchase);
    return purchase.toBuilder().products(productsWithPriceTtc).build();
  }

  @Override
  public Purchase createPurchase() {
    return purchasePort.savePurchase();
  }

  @Override
  public Purchase addProductToPurchase(long idPurchase, long idProduct) {
    Purchase purchase = getPurchase(idPurchase);
    Product product = productPort.findProductById(idProduct);
    Product productWithPriceTtc = priceTtcCalculator.calculer(product);
    Optional<OrderLine> orderLineOptional = orderLinePort.findOrderLine(idPurchase, idProduct);
    OrderLine orderLine;
    if (orderLineOptional.isPresent()) {
      OrderLine existing = orderLineOptional.get();
      orderLine = existing.toBuilder()
        .quantity(new Quantity(existing.getQuantity().getValue() + 1))
        .build();
    } else {
      orderLine = OrderLine.builder()
        .id(OrderLineIdModel.builder().purchaseId(idPurchase).productId(idProduct).build())
        .quantity(new Quantity(1))
        .unitPriceHt(productWithPriceTtc.getPriceHt())
        .unitTax(productWithPriceTtc.getTax())
        .build();
    }
    orderLinePort.saveOrderLine(orderLine);

    List<Product> updatedProducts = new ArrayList<>(purchase.getProducts());
    updatedProducts.add(productWithPriceTtc);

    Purchase purchaseToSave = purchase.toBuilder()
      .totalPrice(new Price(purchase.getTotalPrice().getValue()
        .add(productWithPriceTtc.getPriceTtc().getValue())))
      .totalTax(new TotalTax(purchase.getTotalTax().getValue()
        .add(productWithPriceTtc.getTax().getValue())))
      .build();
    Purchase savedPurchase = purchasePort.savePurchase(purchaseToSave);

    return savedPurchase.toBuilder()
      .products(updatedProducts)
      .build();
  }

  @Override
  public Purchase removeProductFromPurchase(long idPurchase, long idProduct) {
    Purchase purchase = getPurchase(idPurchase);
    Product product = productPort.findProductById(idProduct);
    Optional<OrderLine> orderLineOptional = orderLinePort.findOrderLine(idPurchase, idProduct);
    if (orderLineOptional.isEmpty()) {
      return purchase;
    }
    OrderLine line = orderLineOptional.get();
    if (line.getQuantity().getValue() > 1) {
      line = line.toBuilder()
        .quantity(new Quantity(line.getQuantity().getValue() - 1))
        .build();
      orderLinePort.saveOrderLine(line);
    } else {
      orderLinePort.deleteOrderLine(line);
    }

    Product productWithPriceTtc = priceTtcCalculator.calculer(product);
    List<Product> updatedProducts = new ArrayList<>(purchase.getProducts());
    updatedProducts.removeIf(prod -> productWithPriceTtc.getId().equals(prod.getId()));

    BigDecimal newTotalPrice = subtractNonNegative(purchase.getTotalPrice().getValue(), productWithPriceTtc.getPriceTtc().getValue());
    BigDecimal newTotalTax = subtractNonNegative(purchase.getTotalTax().getValue(), productWithPriceTtc.getTax().getValue());

    Purchase updatePurchase = purchase.toBuilder()
      .totalPrice(new Price(newTotalPrice))
      .totalTax(new TotalTax(newTotalTax))
      .build();
    Purchase savedPurchase = purchasePort.savePurchase(updatePurchase);

    return savedPurchase.toBuilder().products(updatedProducts).build();
  }

  @Override
  public void deletePurchase(long idPurchase) {
    purchasePort.delete(idPurchase);
  }
}
