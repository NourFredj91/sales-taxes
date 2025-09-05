package com.itsf.sales.taxes.adapter.persistance.mapper;

import com.itsf.sales.taxes.adapter.persistance.entity.OrderLineEntity;
import com.itsf.sales.taxes.domain.model.OrderLine;
import com.itsf.sales.taxes.domain.model.Price;
import com.itsf.sales.taxes.domain.model.Quantity;
import com.itsf.sales.taxes.domain.model.TaxRate;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.ERROR,
  uses = {OrderLineIdMapper.class},
  injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface OrderLineEntityMapper {

  OrderLineEntity fromBusinessToEntity(OrderLine orderLine);

  OrderLine fromEntityToBusiness(OrderLineEntity orderLineEntity);

  default Quantity map(int value) {
    return new Quantity(value);
  }

  default int map(Quantity value) {
    return value.getValue();
  }

  default Price map(BigDecimal value) {
    return new Price(value);
  }

  default BigDecimal map(Price value) {
    return value.getValue();
  }

  default TaxRate mapUnitTax(BigDecimal value) {
    return new TaxRate(value);
  }

  default BigDecimal mapUnitTax(TaxRate value) {
    return value.getValue();
  }
}
