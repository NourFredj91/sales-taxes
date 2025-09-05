package com.itsf.sales.taxes.adapter.presentation.mapper;

import com.itsf.sales.taxes.adapter.presentation.dto.PurchaseDto;
import com.itsf.sales.taxes.domain.model.Purchase;
import com.itsf.sales.taxes.domain.model.TotalTax;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.ERROR,
  uses = {ProductDtoMapper.class},
  injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PurchaseDtoMapper {

  PurchaseDto fromBusinessToDto(Purchase purchase);

  default BigDecimal map(TotalTax value) {
    return value.getValue();
  }
}
