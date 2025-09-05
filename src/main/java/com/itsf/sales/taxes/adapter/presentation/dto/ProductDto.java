package com.itsf.sales.taxes.adapter.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itsf.sales.taxes.domain.model.enumeration.ProductType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  @NotNull
  private String name;

  @NotNull
  @Positive
  @Digits(integer = 17, fraction = 2)
  private BigDecimal priceHt;

  private BigDecimal priceTtc;

  private BigDecimal tax;

  @NotNull
  private ProductType productType;
  private boolean imported;

  private int quantity;
}
