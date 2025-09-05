package com.itsf.sales.taxes.adapter.presentation;

import com.itsf.sales.taxes.adapter.presentation.dto.ProductDto;
import com.itsf.sales.taxes.adapter.presentation.mapper.ProductDtoMapper;
import com.itsf.sales.taxes.domain.model.Product;
import com.itsf.sales.taxes.domain.port.in.ProductUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductAdapterInTest {

  @InjectMocks()
  private ProductAdapterIn adapter;

  @Mock
  private ProductDtoMapper mapper;
  @Mock
  private ProductUseCase useCase;

  @Test
  void getAllProducts() {
    // GIVEN
    Product product1 = mock(Product.class);
    Product product2 = mock(Product.class);
    when(useCase.getAllProducts()).thenReturn(asList(product1, product2));

    ProductDto productDto1 = mock(ProductDto.class);
    when(mapper.fromBusinessToDto(product1)).thenReturn(productDto1);
    ProductDto productDto2 = mock(ProductDto.class);
    when(mapper.fromBusinessToDto(product2)).thenReturn(productDto2);

    // WHEN
    List<ProductDto> result = adapter.getAllProducts();

    // THEN
    assertThat(result).containsExactly(productDto1, productDto2);
  }

  @Test
  void getProduct() {
    // GIVEN
    Product product = mock(Product.class);
    when(useCase.getProduct(12L)).thenReturn(product);

    ProductDto productDto = mock(ProductDto.class);
    when(mapper.fromBusinessToDto(product)).thenReturn(productDto);

    // WHEN
    ProductDto result = adapter.getProduct(12L);

    // THEN
    assertThat(result).isEqualTo(productDto);
  }

  @Test
  void addProduct() {
    // GIVEN
    Product product = mock(Product.class);
    ProductDto productDto = mock(ProductDto.class);
    when(mapper.fromDtoToBusiness(productDto)).thenReturn(product);

    when(useCase.addProduct(product)).thenReturn(product);
    when(mapper.fromBusinessToDto(product)).thenReturn(productDto);

    // WHEN
    ProductDto result = adapter.addProduct(productDto);

    // THEN
    assertThat(result).isEqualTo(productDto);
  }

  @Test
  void updateProduct() {
    // GIVEN
    Product product = mock(Product.class);
    ProductDto productDto = mock(ProductDto.class);
    when(mapper.fromDtoToBusiness(productDto)).thenReturn(product);

    when(useCase.updateProduct(11L, product)).thenReturn(product);
    when(mapper.fromBusinessToDto(product)).thenReturn(productDto);

    // WHEN
    ProductDto result = adapter.updateProduct(11L, productDto);

    // THEN
    assertThat(result).isEqualTo(productDto);
  }

  @Test
  void deleteProduct() {
    // WHEN
    adapter.deleteProduct(11L);

    // THEN
    verify(useCase).deleteProduct(11L);
  }
}