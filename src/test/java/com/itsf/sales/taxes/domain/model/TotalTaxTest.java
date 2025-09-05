package com.itsf.sales.taxes.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TotalTaxTest {

  @Test
  void should_accept_zero_values() {
    TotalTax zero = new TotalTax(BigDecimal.ZERO);

    assertThat(zero.getValue()).isEqualByComparingTo("0");
  }

  @Test
  void should_accept_positive_values() {
    TotalTax positive = new TotalTax(new BigDecimal("10.50"));

    assertThat(positive.getValue()).isEqualByComparingTo("10.50");
  }

  @Test
  void should_reject_null_value() {
    assertThrows(IllegalArgumentException.class, () -> new TotalTax(null));
  }

  @Test
  void should_reject_negative_value() {
    assertThrows(IllegalArgumentException.class, () -> new TotalTax(new BigDecimal("-1")));
  }
}