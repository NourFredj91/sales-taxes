package com.itsf.sales.taxes.domain.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class TaxRate {

  BigDecimal value;

  public TaxRate(BigDecimal value) {
    if (value.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Tax must be positive");
    }
    this.value = value;
  }
}
