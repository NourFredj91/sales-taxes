package com.itsf.sales.taxes.domain.model;

import lombok.Value;

@Value
public class Quantity {

  int value;

  public Quantity(int value) {
    if (value < 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }
    this.value = value;
  }
}
