package com.itsf.sales.taxes.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

  @Test
  void should_accept_zero_value() {
    Price zero = new Price(BigDecimal.ZERO);

    assertThat(zero.getValue()).isEqualByComparingTo("0");
  }

  @Test
  void should_accept_positive_value() {
    Price positive = new Price(new BigDecimal("99.99"));

    assertThat(positive.getValue()).isEqualByComparingTo("99.99");
  }

  @Test
  void should_reject_null_value() {
    assertThrows(IllegalArgumentException.class, () -> new Price(null));
  }

  @Test
  void should_reject_negative_value() {
    assertThrows(IllegalArgumentException.class, () -> new Price(new BigDecimal("-0.01")));
  }
}