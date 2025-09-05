package com.itsf.sales.taxes.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NameTest {

  @Test
  void should_accept_non_blank_values() {
    Name name = new Name("Chocolate");
    assertThat(name.getValue()).isEqualTo("Chocolate");
  }

  @Test
  void should_reject_null_value() {
    assertThrows(IllegalArgumentException.class, () -> new Name(null));
  }

  @Test
  void should_reject_empty_value() {
    assertThrows(IllegalArgumentException.class, () -> new Name(""));
  }

  @Test
  void should_reject_blank_value() {
    assertThrows(IllegalArgumentException.class, () -> new Name("   "));
  }
}