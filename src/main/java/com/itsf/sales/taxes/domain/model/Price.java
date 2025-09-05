package com.itsf.sales.taxes.domain.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Price {

  BigDecimal value;

  public Price(BigDecimal value) {
    if (value == null) {
      throw new IllegalArgumentException("Price cannot be null");
    }
    if (value.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Price must be positive");
    }
    this.value = value;
  }
}
