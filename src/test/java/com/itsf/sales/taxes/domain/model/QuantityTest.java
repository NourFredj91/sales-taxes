package com.itsf.sales.taxes.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

  @Test
  void should_accept_zero_values() {
    Quantity zero = new Quantity(0);

    assertThat(zero.getValue()).isEqualTo(0);
  }

  @Test
  void should_accept_positive_values() {
    Quantity five = new Quantity(5);

    assertThat(five.getValue()).isEqualTo(5);
  }

  @Test
  void should_reject_negative_values() {
    assertThrows(IllegalArgumentException.class, () -> new Quantity(-1));
  }
}