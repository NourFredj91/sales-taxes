package com.itsf.sales.taxes.adapter.persistance.mapper;

import com.itsf.sales.taxes.adapter.persistance.entity.OrderLineEntity;
import com.itsf.sales.taxes.adapter.persistance.entity.OrderLineId;
import com.itsf.sales.taxes.domain.model.OrderLine;
import com.itsf.sales.taxes.domain.model.OrderLineIdModel;
import com.itsf.sales.taxes.domain.model.Price;
import com.itsf.sales.taxes.domain.model.Quantity;
import com.itsf.sales.taxes.domain.model.TaxRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class OrderLineEntityMapperTest {

  private OrderLineEntityMapper mapper;

  @Mock
  private OrderLineIdMapper orderLineIdMapper;

  @BeforeEach
  void setUp() {
    mapper = new OrderLineEntityMapperImpl(orderLineIdMapper);
  }

  @Test
  void fromBusinessToEntity() {
    // GIVEN
    OrderLineId entityId = mock(OrderLineId.class);
    OrderLineIdModel businessId = mock(OrderLineIdModel.class);
    when(orderLineIdMapper.fromBusinessToEntity(businessId)).thenReturn(entityId);

    BigDecimal unitPriceHt = new BigDecimal("10.50");
    BigDecimal unitTax = new BigDecimal("2.10");
    OrderLine orderLine = OrderLine.builder()
      .id(businessId)
      .quantity(new Quantity(3))
      .unitPriceHt(new Price(unitPriceHt))
      .unitTax(new TaxRate(unitTax))
      .build();

    // WHEN
    OrderLineEntity result = mapper.fromBusinessToEntity(orderLine);

    // THEN
    assertThat(result.getId()).isEqualTo(entityId);
    assertThat(result.getQuantity()).isEqualTo(3);
    assertThat(result.getUnitPriceHt()).isEqualTo(unitPriceHt);
    assertThat(result.getUnitTax()).isEqualTo(unitTax);
  }

  @Test
  void fromEntityToBusiness() {
    // GIVEN
    OrderLineId entityId = mock(OrderLineId.class);
    OrderLineIdModel businessId = mock(OrderLineIdModel.class);
    when(orderLineIdMapper.fromEntityToBusiness(entityId)).thenReturn(businessId);

    BigDecimal unitPriceHt = new BigDecimal("10.50");
    BigDecimal unitTax = new BigDecimal("2.10");
    OrderLineEntity orderLineEntity = new OrderLineEntity();
    orderLineEntity.setId(entityId);
    orderLineEntity.setQuantity(3);
    orderLineEntity.setUnitPriceHt(unitPriceHt);
    orderLineEntity.setUnitTax(unitTax);

    // WHEN
    OrderLine result = mapper.fromEntityToBusiness(orderLineEntity);

    // THEN
    assertThat(result.getId()).isEqualTo(businessId);
    assertThat(result.getQuantity().getValue()).isEqualTo(3);
    assertThat(result.getUnitPriceHt().getValue()).isEqualTo(unitPriceHt);
    assertThat(result.getUnitTax().getValue()).isEqualTo(unitTax);
  }
}