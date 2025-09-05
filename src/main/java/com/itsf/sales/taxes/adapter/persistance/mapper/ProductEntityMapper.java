package com.itsf.sales.taxes.adapter.persistance.mapper;

import com.itsf.sales.taxes.adapter.persistance.entity.ProductEntity;
import com.itsf.sales.taxes.domain.model.Name;
import com.itsf.sales.taxes.domain.model.Price;
import com.itsf.sales.taxes.domain.model.Product;
import com.itsf.sales.taxes.domain.model.Quantity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductEntityMapper {

  ProductEntity fromBusinessToEntity(Product product);

  @Mapping(target = "priceTtc",  ignore = true)
  @Mapping(target = "tax", ignore = true)
  Product fromEntityToBusiness(ProductEntity productEntity);

  default Name map(String value) {
    return new Name(value);
  }

  default String map(Name value) {
    return value.getValue();
  }

  default Price map(BigDecimal value) {
    return new Price(value);
  }

  default BigDecimal map(Price value) {
    return value.getValue();
  }

  default Quantity map(int value) {
    return new Quantity(value);
  }

  default int map(Quantity value) {
    return value.getValue();
  }
}
