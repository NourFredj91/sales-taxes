package com.itsf.sales.taxes.adapter.persistance.mapper;

import com.itsf.sales.taxes.adapter.persistance.entity.PurchaseEntity;
import com.itsf.sales.taxes.domain.model.Price;
import com.itsf.sales.taxes.domain.model.Purchase;
import com.itsf.sales.taxes.domain.model.TotalTax;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.time.LocalDate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PurchaseEntityMapper {

  @Mapping(target = "date", source = "date")
  @Mapping(target = "totalPrice", source = "purchase.totalPrice.value")
  @Mapping(target = "totalTax", source = "purchase.totalTax.value")
  PurchaseEntity fromBusinessToEntity(Purchase purchase, LocalDate date);

  @Mapping(target = "products", ignore = true)
  Purchase fromEntityToBusiness(PurchaseEntity purchaseEntity);

  default Price map(BigDecimal value) {
    return new Price(value);
  }

  default BigDecimal map(Price value) {
    return value.getValue();
  }

  default TotalTax mapTotalTax(BigDecimal value) {
    return new TotalTax(value);
  }
}
