package com.itsf.sales.taxes.domain.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class TotalTax {

  BigDecimal value;

  public TotalTax(BigDecimal value) {
    if(value == null) {
      throw new IllegalArgumentException("Price cannot be null");
    }
    if (value.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Tax must be positive");
    }
    this.value = value;
  }
}
