package com.itsf.sales.taxes.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TaxRateTest {

  @Test
  void should_accept_zero_values() {
    TaxRate zero = new TaxRate(BigDecimal.ZERO);

    assertThat(zero.getValue()).isEqualByComparingTo("0");
  }

  @Test
  void should_accept_positive_values() {
    TaxRate positive = new TaxRate(new BigDecimal("0.20"));

    assertThat(positive.getValue()).isEqualByComparingTo("0.20");
  }

  @Test
  void should_reject_negative_value() {
    assertThrows(IllegalArgumentException.class, () -> new TaxRate(new BigDecimal("-0.01")));
  }
}