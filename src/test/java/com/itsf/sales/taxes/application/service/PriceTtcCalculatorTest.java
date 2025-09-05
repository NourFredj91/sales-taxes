package com.itsf.sales.taxes.application.service;

import com.itsf.sales.taxes.domain.model.Name;
import com.itsf.sales.taxes.domain.model.Price;
import com.itsf.sales.taxes.domain.model.Product;
import com.itsf.sales.taxes.domain.model.Quantity;
import com.itsf.sales.taxes.domain.model.enumeration.ProductType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PriceTtcCalculatorTest {

  @InjectMocks
  private PriceTtcCalculator calculator;

  @Test
  void calculer_product_not_imported_type_book() {
    // GIVEN
    Product product = Product.builder()
      .name(new Name("Book"))
      .priceHt(new Price(new BigDecimal("12.49")))
      .productType(ProductType.BOOK)
      .imported(false)
      .quantity(new Quantity(4))
      .build();

    // WHEN
    Product result = calculator.calculer(product);

    // THEN
    assertThat(result.getPriceTtc().getValue()).isEqualTo(new BigDecimal("12.49"));
    assertThat(result.getTax().getValue()).isEqualTo(BigDecimal.ZERO.setScale(2, RoundingMode.UNNECESSARY));
  }

  @Test
  void calculer_product_not_imported_type_other() {
    // GIVEN
    Product product = Product.builder()
      .name(new Name("Music CD"))
      .priceHt(new Price(new BigDecimal("14.99")))
      .productType(ProductType.OTHER)
      .imported(false)
      .quantity(new Quantity(4))
      .build();

    // WHEN
    Product result = calculator.calculer(product);

    // THEN
    assertThat(result.getPriceTtc().getValue()).isEqualTo(new BigDecimal("16.49"));
    assertThat(result.getTax().getValue()).isEqualTo(new BigDecimal("1.50"));
  }

  @Test
  void calculer_product_imported_type_food() {
    // GIVEN
    Product product = Product.builder()
      .name(new Name("Chocolate"))
      .priceHt(new Price(new BigDecimal("10.00")))
      .productType(ProductType.FOOD)
      .imported(true)
      .quantity(new Quantity(4))
      .build();

    // WHEN
    Product result = calculator.calculer(product);

    // THEN
    assertThat(result.getPriceTtc().getValue()).isEqualTo(new BigDecimal("10.50"));
    assertThat(result.getTax().getValue()).isEqualTo(new BigDecimal("0.50"));
  }


  @Test
  void calculer_product_imported_type_other() {
    // GIVEN
    Product product = Product.builder()
      .name(new Name("Perfume"))
      .priceHt(new Price(new BigDecimal("57.00")))
      .productType(ProductType.OTHER)
      .imported(true)
      .quantity(new Quantity(4))
      .build();

    // WHEN
    Product result = calculator.calculer(product);

    // THEN
    assertThat(result.getPriceTtc().getValue()).isEqualTo(new BigDecimal("65.55"));
    assertThat(result.getTax().getValue()).isEqualTo(new BigDecimal("8.55"));
  }
}