package com.itsf.sales.taxes.adapter.persistance.repository;

import com.itsf.sales.taxes.adapter.persistance.entity.OrderLineEntity;
import com.itsf.sales.taxes.adapter.persistance.entity.OrderLineId;
import com.itsf.sales.taxes.adapter.persistance.entity.ProductEntity;
import com.itsf.sales.taxes.adapter.persistance.entity.PurchaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.itsf.sales.taxes.domain.model.enumeration.ProductType.FOOD;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OrderLineRepositoryTest {

  @Autowired
  private EntityManager em;

  @Autowired
  private OrderLineRepository repository;

  @Test
  void findAllByIdPurchase() {
    // GIVEN
    PurchaseEntity purchaseEntity = new PurchaseEntity();
    purchaseEntity.setDate(LocalDate.now());
    purchaseEntity.setTotalPrice(new BigDecimal("500.60"));
    purchaseEntity.setTotalTax(new BigDecimal("23.40"));
    purchaseEntity = em.merge(purchaseEntity);

    OrderLineId id = new OrderLineId();
    id.setPurchaseId(purchaseEntity.getId());
    id.setProductId(12L);
    OrderLineEntity orderLineEntity = new OrderLineEntity();
    orderLineEntity.setId(id);
    orderLineEntity.setUnitTax(new BigDecimal("22.30"));
    orderLineEntity.setUnitPriceHt(new BigDecimal("43.30"));
    em.merge(orderLineEntity);

    // WHEN
    List<OrderLineEntity> result = repository.findAllByIdPurchase(purchaseEntity.getId());

    // THEN
    assertThat(result).containsExactly(orderLineEntity);
  }
}