package com.itsf.sales.taxes.adapter.persistance.mapper;

import com.itsf.sales.taxes.adapter.persistance.entity.ProductEntity;
import com.itsf.sales.taxes.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static com.itsf.sales.taxes.domain.model.enumeration.ProductType.FOOD;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
class ProductEntityMapperTest {

  private ProductEntityMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ProductEntityMapperImpl();
  }

  @Test
  void fromEntityToBusiness() {
    // GIVEN
    ProductEntity productEntity = new ProductEntity();
    productEntity.setId(1L);
    productEntity.setQuantity(3);
    productEntity.setProductType(FOOD);
    productEntity.setName("Chocolate");
    BigDecimal price = new BigDecimal("23.45");
    productEntity.setPriceHt(price);
    productEntity.setImported(false);

    // WHEN
    Product result = mapper.fromEntityToBusiness(productEntity);

    // THEN
    assertAll(
      () -> assertEquals(1L, result.getId()),
      () -> assertEquals(3, result.getQuantity().getValue()),
      () -> assertEquals("Chocolate", result.getName().getValue()),
      () -> assertEquals(price, result.getPriceHt().getValue()),
      () -> assertFalse(result.isImported())
    );
  }
}