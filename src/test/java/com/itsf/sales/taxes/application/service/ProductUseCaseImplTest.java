package com.itsf.sales.taxes.application.service;

import com.itsf.sales.taxes.domain.model.Name;
import com.itsf.sales.taxes.domain.model.Price;
import com.itsf.sales.taxes.domain.model.PriceTtc;
import com.itsf.sales.taxes.domain.model.Product;
import com.itsf.sales.taxes.domain.model.Quantity;
import com.itsf.sales.taxes.domain.model.TaxRate;
import com.itsf.sales.taxes.domain.model.enumeration.ProductType;
import com.itsf.sales.taxes.domain.port.out.ProductPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseImplTest {

  @InjectMocks
  private ProductUseCaseImpl useCase;

  @Mock
  private ProductPort port;

  @Test
  void getAllProducts() {
    // GIVEN
    Product product1 = mock(Product.class);
    Product product2 = mock(Product.class);
    when(port.findAllProducts()).thenReturn(asList(product1, product2));

    // WHEN
    List<Product>  result = useCase.getAllProducts();

    // THEN
    assertThat(result).containsExactly(product1, product2);
  }

  @Test
  void getProduct() {
    // GIVEN
    Product product = mock(Product.class);
    when(port.findProductById(12L)).thenReturn(product);

    // WHEN
    Product result = useCase.getProduct(12L);

    // THEN
    assertThat(result).isEqualTo(product);
  }

  @Test
  void addProduct() {
    // GIVEN
    Product product = mock(Product.class);
    when(port.saveProduct(product)).thenReturn(product);

    // WHEN
    Product result = useCase.addProduct(product);

    // THEN
    assertThat(result).isEqualTo(product);
  }

  @Test
  void updateProduct() {
    // GIVEN
    Product existingProduct = Product.builder()
      .id(11L)
      .name(new Name("book"))
      .priceHt(new Price(new BigDecimal("24.70")))
      .priceTtc(new PriceTtc(new BigDecimal("30.70")))
      .tax(new TaxRate(new BigDecimal("0.5")))
      .productType(ProductType.BOOK)
      .imported(false)
      .quantity(new Quantity(5))
      .build();
    when(port.findProductById(11L)).thenReturn(existingProduct);
    Product updatingProduct = Product.builder()
      .id(11L)
      .name(new Name("Chocolate"))
      .priceHt(new Price(new BigDecimal("54.70")))
      .priceTtc(new PriceTtc(new BigDecimal("60.70")))
      .tax(new TaxRate(new BigDecimal("1.5")))
      .productType(ProductType.FOOD)
      .imported(true)
      .quantity(new Quantity(5))
      .build();

    Product savedProduct = existingProduct.toBuilder()
      .id(11L)
      .name(new Name("Chocolate"))
      .priceHt(new Price(new BigDecimal("54.70")))
      .priceTtc(new PriceTtc(new BigDecimal("60.70")))
      .tax(new TaxRate(new BigDecimal("1.5")))
      .productType(ProductType.FOOD)
      .imported(true)
      .quantity(new Quantity(5))
      .build();
    when(port.saveProduct(any())).thenReturn(savedProduct);

    // WHEN
    Product result = useCase.updateProduct(11L, updatingProduct);

    // THEN
    assertThat(result).usingRecursiveComparison().isEqualTo(savedProduct);
  }

  @Test
  void deleteProduct() {
    // WHEN
    useCase.deleteProduct(11L);

    // THEN

    verify(port).deleteProduct(11L);
  }
}