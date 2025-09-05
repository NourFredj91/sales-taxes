package com.itsf.sales.taxes.domain.port.in;

import com.itsf.sales.taxes.domain.model.Product;

import java.util.List;

public interface ProductUseCase {

  List<Product> getAllProducts();
  Product getProduct(long idProduct);
  Product addProduct(Product product);
  Product updateProduct(long productId, Product product);
  void deleteProduct(long idProduct);
}
