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

import static com.itsf.sales.taxes.domain.model.enumeration.ProductType.BOOK;
import static com.itsf.sales.taxes.domain.model.enumeration.ProductType.FOOD;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductRepositoryTest {

  @Autowired
  private EntityManager em;

  @Autowired
  private ProductRepository repository;

  @Test
  void findProductsByIdPurchase() {
    // GIVEN
    PurchaseEntity purchaseEntity = new PurchaseEntity();
    purchaseEntity.setDate(LocalDate.now());
    purchaseEntity.setTotalPrice(new BigDecimal("500.60"));
    purchaseEntity.setTotalTax(new BigDecimal("23.40"));
    purchaseEntity = em.merge(purchaseEntity);

    ProductEntity productEntity1 = new ProductEntity();
    productEntity1.setProductType(FOOD);
    productEntity1.setName("Chocolate");
    productEntity1.setImported(false);
    productEntity1.setPriceHt(new BigDecimal("34.54"));
    productEntity1 = em.merge(productEntity1);

    ProductEntity productEntity2 = new ProductEntity();
    productEntity2.setProductType(BOOK);
    productEntity2.setName("Book");
    productEntity2.setImported(true);
    productEntity2.setPriceHt(new BigDecimal("54.54"));
    productEntity2 = em.merge(productEntity2);

    OrderLineId id1 = new OrderLineId();
    id1.setPurchaseId(purchaseEntity.getId());
    id1.setProductId(productEntity1.getId());
    OrderLineEntity orderLineEntity1 = new OrderLineEntity();
    orderLineEntity1.setId(id1);
    orderLineEntity1.setUnitTax(new BigDecimal("22.30"));
    orderLineEntity1.setUnitPriceHt(new BigDecimal("43.30"));
    em.merge(orderLineEntity1);

    OrderLineId id2 = new OrderLineId();
    id2.setPurchaseId(purchaseEntity.getId());
    id2.setProductId(productEntity2.getId());
    OrderLineEntity orderLineEntity2 = new OrderLineEntity();
    orderLineEntity2.setId(id2);
    orderLineEntity2.setUnitTax(new BigDecimal("62.30"));
    orderLineEntity2.setUnitPriceHt(new BigDecimal("83.30"));
    em.merge(orderLineEntity2);

    // WHEN
    List<ProductEntity> result = repository.findProductsByIdPurchase(purchaseEntity.getId());

    // THEN
    assertThat(result).containsExactly(productEntity1,productEntity2);
  }
}