package com.itsf.sales.taxes.adapter.persistance.mapper;

import com.itsf.sales.taxes.adapter.persistance.entity.OrderLineId;
import com.itsf.sales.taxes.domain.model.OrderLineIdModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderLineIdMapper {

  @Mapping(target = "purchaseId", source = "purchaseId")
  @Mapping(target = "productId", source = "productId")
  OrderLineId fromBusinessToEntity(OrderLineIdModel orderLineIdModel);

  @Mapping(target = "purchaseId", source = "purchaseId")
  @Mapping(target = "productId", source = "productId")
  OrderLineIdModel fromEntityToBusiness(OrderLineId orderLineId);
}
