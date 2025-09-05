package com.itsf.sales.taxes.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PriceTtcTest {

  @Test
  void should_accept_zero_values() {
    PriceTtc zero = new PriceTtc(BigDecimal.ZERO);
    PriceTtc positive = new PriceTtc(new BigDecimal("42.55"));

    assertThat(zero.getValue()).isEqualByComparingTo("0");
    assertThat(positive.getValue()).isEqualByComparingTo("42.55");
  }

  @Test
  void should_accept_positive_values() {
    PriceTtc positive = new PriceTtc(new BigDecimal("42.55"));

    assertThat(positive.getValue()).isEqualByComparingTo("42.55");
  }

  @Test
  void should_reject_negative_value() {
    assertThrows(IllegalArgumentException.class, () -> new PriceTtc(new BigDecimal("-0.01")));
  }
}