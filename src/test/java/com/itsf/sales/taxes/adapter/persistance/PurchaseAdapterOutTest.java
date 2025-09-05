package com.itsf.sales.taxes.adapter.persistance;

import com.itsf.sales.taxes.adapter.persistance.entity.OrderLineEntity;
import com.itsf.sales.taxes.adapter.persistance.entity.PurchaseEntity;
import com.itsf.sales.taxes.adapter.persistance.exception.ResourceNotFoundException;
import com.itsf.sales.taxes.adapter.persistance.mapper.PurchaseEntityMapper;
import com.itsf.sales.taxes.adapter.persistance.repository.OrderLineRepository;
import com.itsf.sales.taxes.adapter.persistance.repository.PurchaseRepository;
import com.itsf.sales.taxes.domain.model.Purchase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseAdapterOutTest {

  @InjectMocks
  private PurchaseAdapterOut adapter;

  @Mock
  private PurchaseRepository purchaseRepository;
  @Mock
  private OrderLineRepository orderLineRepository;
  @Mock
  private PurchaseEntityMapper mapper;

  @Test
  void findPurchaseById() {
    // GIVEN
    PurchaseEntity purchaseEntity = mock(PurchaseEntity.class);
    when(purchaseRepository.findById(12L)).thenReturn(Optional.of(purchaseEntity));

    Purchase purchase = mock(Purchase.class);
    when(mapper.fromEntityToBusiness(purchaseEntity)).thenReturn(purchase);

    // WHEN
    Purchase result = adapter.findPurchaseById(12L);

    //THEN
    assertThat(result).isEqualTo(purchase);
  }

  @Test
  void findPurchaseById_throws_exception() {
    // GIVEN
    when(purchaseRepository.findById(12L)).thenReturn(Optional.empty());

    // WHEN
    assertThrows(ResourceNotFoundException.class, () -> adapter.findPurchaseById(12L));
  }

  @Test
  void savePurchase_add() {
    // GIVEN
    PurchaseEntity purchaseEntity = new PurchaseEntity();
    purchaseEntity.setDate(LocalDate.now());
    purchaseEntity.setTotalTax(BigDecimal.ZERO);
    purchaseEntity.setTotalPrice(BigDecimal.ZERO);
    when(purchaseRepository.save(purchaseEntity)).thenReturn(purchaseEntity);

    Purchase purchase = mock(Purchase.class);
    when(mapper.fromEntityToBusiness(purchaseEntity)).thenReturn(purchase);

    // WHEN
    Purchase result = adapter.savePurchase();

    // THEN
    assertThat(result).isEqualTo(purchase);
  }

  @Test
  void savePurchase_modification() {
    // GIVEN
    PurchaseEntity purchaseEntity = mock(PurchaseEntity.class);
    Purchase purchase = mock(Purchase.class);
    LocalDate today = LocalDate.now();
    when(mapper.fromBusinessToEntity(purchase, today)).thenReturn(purchaseEntity);

    when(purchaseRepository.save(purchaseEntity)).thenReturn(purchaseEntity);
    when(mapper.fromEntityToBusiness(purchaseEntity)).thenReturn(purchase);

    // WHEN
    Purchase result = adapter.savePurchase(purchase);

    // THEN
    assertThat(result).isEqualTo(purchase);
  }

  @Test
  void delete() {
    // GIVEN
    OrderLineEntity orderLineEntity1 = mock(OrderLineEntity.class);
    OrderLineEntity orderLineEntity2 = mock(OrderLineEntity.class);
    List<OrderLineEntity> orderLineEntityList = asList(orderLineEntity1, orderLineEntity2);
    when(orderLineRepository.findAllByIdPurchase(12L)).thenReturn(orderLineEntityList);

    // WHEN
    adapter.delete(12L);

    // THEN
    verify(orderLineRepository).deleteAll(orderLineEntityList);
    verify(purchaseRepository).deleteById(12L);
  }
}