package com.itsf.sales.taxes.application.service;

import com.itsf.sales.taxes.domain.model.OrderLine;
import com.itsf.sales.taxes.domain.model.OrderLineIdModel;
import com.itsf.sales.taxes.domain.model.Price;
import com.itsf.sales.taxes.domain.model.PriceTtc;
import com.itsf.sales.taxes.domain.model.Product;
import com.itsf.sales.taxes.domain.model.Purchase;
import com.itsf.sales.taxes.domain.model.Quantity;
import com.itsf.sales.taxes.domain.model.TaxRate;
import com.itsf.sales.taxes.domain.model.TotalTax;
import com.itsf.sales.taxes.domain.port.out.OrderLinePort;
import com.itsf.sales.taxes.domain.port.out.ProductPort;
import com.itsf.sales.taxes.domain.port.out.PurchasePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PurchaseUseCaseImplTest {

  @InjectMocks
  private PurchaseUseCaseImpl useCase;

  @Mock
  private PurchasePort purchasePort;
  @Mock
  private ProductPort productPort;
  @Mock
  private OrderLinePort orderLinePort;
  @Mock
  private PriceTtcCalculator calculator;

  @Test
  void getPurchase() {
    // GIVEN
    Product product = mock(Product.class);
    when(productPort.findProductByIdPurchase(3L)).thenReturn(singletonList(product));

    Product productWithPriceTtc = mock(Product.class);
    when(calculator.calculer(product)).thenReturn(productWithPriceTtc);

    Price totalPrice = new Price(new BigDecimal("400.50"));
    TotalTax totalTax = new TotalTax(new BigDecimal("20.55"));
    Purchase purchase = Purchase.builder().id(3L).totalPrice(totalPrice).totalTax(totalTax).build();
    when(purchasePort.findPurchaseById(3L)).thenReturn(purchase);

    // WHEN
    Purchase result = useCase.getPurchase(3L);

    // THEN
    assertThat(result.getProducts()).containsExactly(productWithPriceTtc);
    assertThat(result.getId()).isEqualTo(purchase.getId());
    assertThat(result.getTotalTax()).isEqualTo(purchase.getTotalTax());
    assertThat(result.getTotalPrice()).isEqualTo(purchase.getTotalPrice());
  }

  @Test
  void createPurchase() {
    // GIVEN
    Purchase purchase = mock(Purchase.class);
    when(purchasePort.savePurchase()).thenReturn(purchase);

    // WHEN
    Purchase result = useCase.createPurchase();

    // THEN
    assertThat(result).isEqualTo(purchase);
  }

  @Test
  void addProductToPurchase_incrementsExistingOrderLine_andUpdatesTotals() {
    // GIVEN
    // existing Products
    Product existingProduct = mock(Product.class);
    when(productPort.findProductByIdPurchase(3L)).thenReturn(singletonList(existingProduct));

    Product existingProductTtc = mock(Product.class);
    when(calculator.calculer(existingProduct)).thenReturn(existingProductTtc);

    Purchase purchase = Purchase.builder().id(3L)
      .totalPrice(new Price(new BigDecimal("400.50")))
      .totalTax(new TotalTax(new BigDecimal("20.55")))
      .products(singletonList(existingProductTtc))
      .build();
    when(purchasePort.findPurchaseById(3L)).thenReturn(purchase);

    // products to add
    Product productToAdd = mock(Product.class);
    when(productPort.findProductById(2L)).thenReturn(productToAdd);

    Product productToAddWithPriceTtc = mock(Product.class);
    when(calculator.calculer(productToAdd)).thenReturn(productToAddWithPriceTtc);

    when(productToAddWithPriceTtc.getPriceTtc()).thenReturn(new PriceTtc(new BigDecimal("42.00")));
    when(productToAddWithPriceTtc.getTax()).thenReturn(new TaxRate(new BigDecimal("2.00")));

    OrderLine orderLine = OrderLine.builder()
      .id(OrderLineIdModel.builder().productId(2L).purchaseId(3L).build())
      .quantity(new Quantity(3))
      .unitPriceHt(new Price(new BigDecimal("34.90")))
      .unitTax(new TaxRate(new BigDecimal("40.3")))
      .build();
    when(orderLinePort.findOrderLine(3L, 2L)).thenReturn(Optional.of(orderLine));

    Purchase savedPurchase = purchase.toBuilder()
      .id(3L)
      .totalPrice(new Price(new BigDecimal("442.50")))
      .totalTax(new TotalTax(new BigDecimal("22.55")))
      .products(singletonList(existingProductTtc))
      .build();
    when(purchasePort.savePurchase(any(Purchase.class))).thenReturn(savedPurchase);

    // WHEN
    Purchase result = useCase.addProductToPurchase(3L, 2L);

    // THEN
    assertThat(result.getProducts()).containsExactly(existingProductTtc, productToAddWithPriceTtc);
    assertThat(result.getTotalPrice().getValue()).isEqualByComparingTo("442.50");
    assertThat(result.getTotalTax().getValue()).isEqualByComparingTo("22.55");
  }

  @Test
  void addProductToPurchase_no_existing_order_line() {
    // GIVEN
    Product existingProduct = mock(Product.class);
    when(productPort.findProductByIdPurchase(3L)).thenReturn(singletonList(existingProduct));

    Product existingProductWithTtc = mock(Product.class);
    when(calculator.calculer(existingProduct)).thenReturn(existingProductWithTtc);

    Purchase purchase = Purchase.builder().id(3L)
      .totalPrice(new Price(new BigDecimal("400.50")))
      .totalTax(new TotalTax(new BigDecimal("20.55")))
      .products(singletonList(existingProductWithTtc))
      .build();
    when(purchasePort.findPurchaseById(3L)).thenReturn(purchase);

    Product productToAdd = mock(Product.class);
    when(productPort.findProductById(2L)).thenReturn(productToAdd);

    Product productToAddWithPriceTtc = mock(Product.class);
    when(calculator.calculer(productToAdd)).thenReturn(productToAddWithPriceTtc);
    when(productToAddWithPriceTtc.getPriceTtc()).thenReturn(new PriceTtc(new BigDecimal("42.00")));
    when(productToAddWithPriceTtc.getTax()).thenReturn(new TaxRate(new BigDecimal("2.00")));

    when(orderLinePort.findOrderLine(3L, 2L)).thenReturn(Optional.empty());

    Purchase savedPurchase = purchase.toBuilder()
      .id(3L)
      .totalPrice(new Price(new BigDecimal("442.50")))
      .totalTax(new TotalTax(new BigDecimal("22.55")))
      .products(singletonList(existingProductWithTtc))
      .build();
    when(purchasePort.savePurchase(any(Purchase.class))).thenReturn(savedPurchase);

    // WHEN
    Purchase result = useCase.addProductToPurchase(3L, 2L);

    // THEN
    assertThat(result.getProducts()).containsExactly(existingProductWithTtc, productToAddWithPriceTtc);
    assertThat(result.getTotalPrice().getValue()).isEqualByComparingTo("442.50");
    assertThat(result.getTotalTax().getValue()).isEqualByComparingTo("22.55");
  }

  @Test
  void removeProductFromPurchase() {
    // GIVEN
    // existing Products
    Product existingProduct1 = mock(Product.class);
    Product existingProduct2 = Product.builder().id(1L).build();
    when(productPort.findProductByIdPurchase(3L)).thenReturn(asList(existingProduct1, existingProduct2));

    Product existingProductTtc1 = mock(Product.class);
    when(calculator.calculer(existingProduct1)).thenReturn(existingProductTtc1);
    Product existingProductTtc2 = Product.builder()
      .id(1L)
      .priceTtc(new PriceTtc(new BigDecimal("30.45")))
      .tax(new TaxRate(new BigDecimal("12.34")))
      .build();
    when(calculator.calculer(existingProduct2)).thenReturn(existingProductTtc2);

    Purchase purchase = Purchase.builder().id(3L)
      .totalPrice(new Price(new BigDecimal("400.50")))
      .totalTax(new TotalTax(new BigDecimal("20.55")))
      .products(asList(existingProduct1, existingProduct2))
      .build();
    when(purchasePort.findPurchaseById(3L)).thenReturn(purchase);

    // products to remove
    when(productPort.findProductById(1L)).thenReturn(existingProduct2);

    OrderLine orderLine = OrderLine.builder()
      .id(OrderLineIdModel.builder().productId(1L).purchaseId(3L).build())
      .quantity(new Quantity(3))
      .unitPriceHt(new Price(new BigDecimal("34.90")))
      .unitTax(new TaxRate(new BigDecimal("40.3")))
      .build();
    when(orderLinePort.findOrderLine(3L, 1L)).thenReturn(Optional.of(orderLine));

    Purchase savedPurchase = purchase.toBuilder()
      .id(3L)
      .totalPrice(new Price(new BigDecimal("442.50")))
      .totalTax(new TotalTax(new BigDecimal("22.55")))
      .products(singletonList(existingProduct1))
      .build();
    when(purchasePort.savePurchase(any(Purchase.class))).thenReturn(savedPurchase);

    // WHEN
    Purchase result = useCase.removeProductFromPurchase(3L, 1L);

    // THEN
    assertThat(result.getProducts()).containsExactly(existingProductTtc1);
    assertThat(result.getTotalPrice().getValue()).isEqualByComparingTo("442.50");
    assertThat(result.getTotalTax().getValue()).isEqualByComparingTo("22.55");
  }

  @Test
  void deletePurchase() {
    // WHEN
    useCase.deletePurchase(3L);

    // THEN
    verify(purchasePort).delete(3L);
  }
}