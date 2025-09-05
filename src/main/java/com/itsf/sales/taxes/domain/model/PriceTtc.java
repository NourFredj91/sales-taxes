package com.itsf.sales.taxes.domain.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PriceTtc {

  BigDecimal value;

  public PriceTtc(BigDecimal value) {
    if (value.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Price must be positive");
    }

    this.value = value;
  }
}
