package com.itsf.sales.taxes.adapter.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PurchaseDto {

  private long id;

  @NotNull
  @Positive
  @Digits(integer = 17, fraction = 2)
  private BigDecimal totalPrice;

  @NotNull
  @PositiveOrZero
  @Digits(integer = 17, fraction = 2)
  private BigDecimal totalTax;

  @Valid
  private List<ProductDto> products;
}
