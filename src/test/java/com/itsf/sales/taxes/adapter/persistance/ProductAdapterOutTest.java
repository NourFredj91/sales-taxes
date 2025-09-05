package com.itsf.sales.taxes.adapter.persistance;

import com.itsf.sales.taxes.adapter.persistance.entity.ProductEntity;
import com.itsf.sales.taxes.adapter.persistance.exception.ResourceNotFoundException;
import com.itsf.sales.taxes.adapter.persistance.mapper.ProductEntityMapper;
import com.itsf.sales.taxes.adapter.persistance.repository.ProductRepository;
import com.itsf.sales.taxes.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductAdapterOutTest {

  @InjectMocks
  private ProductAdapterOut adapter;

  @Mock
  private ProductRepository repository;
  @Mock
  private ProductEntityMapper mapper;

  @Test
  void findAllProducts() {
    // GIVEN
    ProductEntity productEntity1 = mock(ProductEntity.class);
    ProductEntity productEntity2 = mock(ProductEntity.class);
    when(repository.findAll()).thenReturn(asList(productEntity1, productEntity2));

    Product product1 = mock(Product.class);
    when(mapper.fromEntityToBusiness(productEntity1)).thenReturn(product1);

    Product product2 = mock(Product.class);
    when(mapper.fromEntityToBusiness(productEntity2)).thenReturn(product2);

    // WHEN
    List<Product> result = adapter.findAllProducts();

    // THEN
    assertThat(result).containsExactly(product1,product2);
  }

  @Test
  void findProductById() {
    // GIVEN
    ProductEntity productEntity = mock(ProductEntity.class);
    when(repository.findById(1L)).thenReturn(Optional.of(productEntity));

    Product product = mock(Product.class);
    when(mapper.fromEntityToBusiness(productEntity)).thenReturn(product);

    // WHEN
    Product result = adapter.findProductById(1L);

    // THEN
    assertThat(result).isEqualTo(product);
  }

  @Test
  void findProductById_throws_exception() {
    // GIVEN
    when(repository.findById(1L)).thenReturn(Optional.empty());

    // WHEN - THEN
   assertThrows(ResourceNotFoundException.class, () -> adapter.findProductById(1L));
  }

  @Test
  void saveProduct() {
    // GIVEN
    Product product = mock(Product.class);
    ProductEntity productEntity = mock(ProductEntity.class);
    when(mapper.fromBusinessToEntity(product)).thenReturn(productEntity);

    when(repository.save(productEntity)).thenReturn(productEntity);
    when(mapper.fromEntityToBusiness(productEntity)).thenReturn(product);

    // WHEN
    Product result = adapter.saveProduct(product);

    // THEN
    assertThat(result).isEqualTo(product);
  }

  @Test
  void deleteProduct() {
    // GIVEN - WHEN
    adapter.deleteProduct(12L);

    // THEN
    verify(repository).deleteById(12L);
  }

  @Test
  void findProductByIdPurchase() {
    // GIVEN
    ProductEntity productEntity1 = mock(ProductEntity.class);
    ProductEntity productEntity2 = mock(ProductEntity.class);
    when(repository.findProductsByIdPurchase(12L)).thenReturn(asList(productEntity1, productEntity2));

    Product product1 = mock(Product.class);
    when(mapper.fromEntityToBusiness(productEntity1)).thenReturn(product1);

    Product product2 = mock(Product.class);
    when(mapper.fromEntityToBusiness(productEntity2)).thenReturn(product2);

    // WHEN
    List<Product> result = adapter.findProductByIdPurchase(12L);

    // THEN
    assertThat(result).containsExactly(product1,product2);
  }
}