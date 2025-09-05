package com.itsf.sales.taxes.domain.port.out;

import com.itsf.sales.taxes.domain.model.Product;

import java.util.List;

public interface ProductPort {

  List<Product> findAllProducts();

  Product findProductById(long idProduct);

  Product saveProduct(Product product);

  void deleteProduct(long idProduct);

  List<Product> findProductByIdPurchase(long idPurchase);
}
