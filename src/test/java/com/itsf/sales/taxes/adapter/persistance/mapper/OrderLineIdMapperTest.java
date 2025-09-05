package com.itsf.sales.taxes.adapter.persistance.mapper;

import com.itsf.sales.taxes.adapter.persistance.entity.OrderLineId;
import com.itsf.sales.taxes.domain.model.OrderLineIdModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
class OrderLineIdMapperTest {

  private OrderLineIdMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new OrderLineIdMapperImpl();
  }

  @Test
  void fromBusinessToEntity() {
    // GIVEN
    OrderLineIdModel orderLineIdModel = OrderLineIdModel.builder()
      .purchaseId(12L)
      .productId(11L)
      .build();

    // WHEN
    OrderLineId result = mapper.fromBusinessToEntity(orderLineIdModel);

    // THEN
    assertThat(result.getProductId()).isEqualTo(11L);
    assertThat(result.getPurchaseId()).isEqualTo(12L);
  }

  @Test
  void fromEntityToBusiness() {
    // GIVEN
    OrderLineId orderLineId = new OrderLineId();
    orderLineId.setProductId(11L);
    orderLineId.setPurchaseId(12L);

    // WHEN
    OrderLineIdModel result = mapper.fromEntityToBusiness(orderLineId);

    // THEN
    assertThat(result.getProductId()).isEqualTo(11L);
    assertThat(result.getPurchaseId()).isEqualTo(12L);
  }
}