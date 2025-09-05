package com.itsf.sales.taxes.adapter.presentation.mapper;

import com.itsf.sales.taxes.adapter.presentation.dto.ProductDto;
import com.itsf.sales.taxes.domain.model.Name;
import com.itsf.sales.taxes.domain.model.Price;
import com.itsf.sales.taxes.domain.model.Product;
import com.itsf.sales.taxes.domain.model.Quantity;
import com.itsf.sales.taxes.domain.model.PriceTtc;
import com.itsf.sales.taxes.domain.model.TaxRate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductDtoMapper {

  Product fromDtoToBusiness(ProductDto productDto);

  ProductDto fromBusinessToDto(Product product);

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

  default PriceTtc mapPriceTtc(BigDecimal value) {
    return value == null ? null : new PriceTtc(value);
  }

  default BigDecimal mapPriceTtc(PriceTtc value) {
   return value == null ? null : value.getValue();
  }

  default TaxRate mapTaxRate(BigDecimal value) {
    return value == null ? null : new TaxRate(value);
  }

  default BigDecimal mapTaxRate(TaxRate value) {
    return value == null ? null : value.getValue();
  }

  default Quantity map(int value) {
    return new Quantity(value);
  }

  default int map(Quantity value) {
    return value.getValue();
  }
}
