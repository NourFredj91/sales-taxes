package com.itsf.sales.taxes.adapter.persistance.mapper;

import com.itsf.sales.taxes.adapter.persistance.entity.PurchaseEntity;
import com.itsf.sales.taxes.domain.model.Price;
import com.itsf.sales.taxes.domain.model.Purchase;
import com.itsf.sales.taxes.domain.model.TotalTax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class PurchaseEntityMapperTest {

  private PurchaseEntityMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new PurchaseEntityMapperImpl();
  }

  @Test
  void fromBusinessToEntity() {
    // GIVEN
    BigDecimal totalPrice = new BigDecimal("10.50");
    BigDecimal totalTax = new BigDecimal("3.50");
    Purchase purchase = Purchase.builder()
      .totalPrice(new Price(totalPrice))
      .totalTax(new TotalTax(totalTax))
      .products(emptyList())
      .build();
    LocalDate today = LocalDate.now();

    // WHEN
    PurchaseEntity result = mapper.fromBusinessToEntity(purchase, today);

    // THEN
    assertAll(
      () -> assertEquals(totalPrice, result.getTotalPrice()),
      () -> assertEquals(totalTax, result.getTotalTax()),
      () -> assertEquals(today, result.getDate())
    );
  }

  @Test
  void fromEntityToBusiness() {
    // GIVEN
    BigDecimal totalPrice = new BigDecimal("10.50");
    BigDecimal totalTax = new BigDecimal("3.50");
    PurchaseEntity purchaseEntity = new PurchaseEntity();
    purchaseEntity.setId(1L);
    purchaseEntity.setTotalPrice(totalPrice);
    purchaseEntity.setTotalTax(totalTax);

    LocalDate today = LocalDate.now();
    purchaseEntity.setDate(today);

    // WHEN
    Purchase result = mapper.fromEntityToBusiness(purchaseEntity);

    // THEN
    assertAll(
      () -> assertEquals(totalPrice, result.getTotalPrice().getValue()),
      () -> assertEquals(totalTax, result.getTotalTax().getValue())
    );
  }
}