package com.itsf.sales.taxes.adapter.persistance;

import com.itsf.sales.taxes.adapter.persistance.entity.OrderLineEntity;
import com.itsf.sales.taxes.adapter.persistance.entity.OrderLineId;
import com.itsf.sales.taxes.adapter.persistance.mapper.OrderLineEntityMapper;
import com.itsf.sales.taxes.adapter.persistance.repository.OrderLineRepository;
import com.itsf.sales.taxes.domain.model.OrderLine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderLineAdapterOutTest {

  @InjectMocks
  private OrderLineAdapterOut adapter;

  @Mock
  private OrderLineRepository repository;
  @Mock
  private OrderLineEntityMapper mapper;

  @Test
  void findOrderLine() {
    // GIVEN
    OrderLineId orderLineId = new OrderLineId();
    orderLineId.setProductId(1L);
    orderLineId.setPurchaseId(21L);

    OrderLineEntity entity = mock(OrderLineEntity.class);
    when(repository.findById(orderLineId)).thenReturn(Optional.of(entity));

    OrderLine orderLine = mock(OrderLine.class);
    when(mapper.fromEntityToBusiness(entity)).thenReturn(orderLine);

    // WHEN
    Optional<OrderLine> result = adapter.findOrderLine(21L, 1L);

    // THEN
    assertThat(result).hasValue(orderLine);
  }

  @Test
  void saveOrderLine() {
    // GIVEN
    OrderLine orderLine = mock(OrderLine.class);

    OrderLineEntity entity = mock(OrderLineEntity.class);
    when(mapper.fromBusinessToEntity(orderLine)).thenReturn(entity);

    // WHEN
    adapter.saveOrderLine(orderLine);

    // THEN
    verify(repository).save(entity);
  }

  @Test
  void deleteOrderLine() {
    // GIVEN
    OrderLine orderLine = mock(OrderLine.class);

    OrderLineEntity entity = mock(OrderLineEntity.class);
    when(mapper.fromBusinessToEntity(orderLine)).thenReturn(entity);

    // WHEN
    adapter.deleteOrderLine(orderLine);

    // THEN
    verify(repository).delete(entity);
  }
}