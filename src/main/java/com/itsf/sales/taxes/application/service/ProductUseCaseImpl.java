package com.itsf.sales.taxes.application.service;

import com.itsf.sales.taxes.domain.model.Product;
import com.itsf.sales.taxes.domain.port.in.ProductUseCase;
import com.itsf.sales.taxes.domain.port.out.ProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductUseCaseImpl implements ProductUseCase {

  private final ProductPort productPort;

  @Override
  public List<Product> getAllProducts() {
    return productPort.findAllProducts();
  }

  @Override
  public Product getProduct(final long idProduct) {
    return productPort.findProductById(idProduct);
  }

  @Override
  public Product addProduct(final Product product) {
    return productPort.saveProduct(product);
  }

  @Override
  public Product updateProduct(final long productId, final Product product) {
    Product existingProduct = productPort.findProductById(productId);

    Product updatedProduct = existingProduct.toBuilder()
      .name(product.getName())
      .priceHt(product.getPriceHt())
      .productType(product.getProductType())
      .imported(product.isImported())
      .quantity(product.getQuantity())
      .build();
    return productPort.saveProduct(updatedProduct);
  }

  @Override
  public void deleteProduct(final long idProduct) {
    productPort.deleteProduct(idProduct);
  }
}
