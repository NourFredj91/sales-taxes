package com.itsf.sales.taxes.adapter.persistance;

import com.itsf.sales.taxes.adapter.persistance.entity.ProductEntity;
import com.itsf.sales.taxes.adapter.persistance.exception.ResourceNotFoundException;
import com.itsf.sales.taxes.adapter.persistance.mapper.ProductEntityMapper;
import com.itsf.sales.taxes.adapter.persistance.repository.ProductRepository;
import com.itsf.sales.taxes.domain.model.Product;
import com.itsf.sales.taxes.domain.port.out.ProductPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductAdapterOut implements ProductPort {

  private final ProductRepository productRepository;
  private final ProductEntityMapper productEntityMapper;

  @Override
  public List<Product> findAllProducts() {
    List<ProductEntity> products = productRepository.findAll();
    return products.stream().map(productEntityMapper::fromEntityToBusiness).toList();
  }

  @Override
  public Product findProductById(final long idProduct) {
    return productRepository.findById(idProduct)
      .map(productEntityMapper::fromEntityToBusiness)
      .orElseThrow(() -> new ResourceNotFoundException("Product " + idProduct + " not found"));
  }

  @Override
  public Product saveProduct(Product product) {
    ProductEntity productEntity = productRepository.save(productEntityMapper.fromBusinessToEntity(product));
    return productEntityMapper.fromEntityToBusiness(productEntity);
  }

  @Override
  public void deleteProduct(long idProduct) {
    productRepository.deleteById(idProduct);
  }

  @Override
  public List<Product> findProductByIdPurchase(long idPurchase) {
    return productRepository.findProductsByIdPurchase(idPurchase)
      .stream()
      .map(productEntityMapper::fromEntityToBusiness).toList();
  }
}
