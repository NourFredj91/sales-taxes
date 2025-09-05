package com.itsf.sales.taxes.domain.model;

import lombok.Value;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Value
public class Name {

  String value;

  public Name(String value) {
    if (isBlank(value)) {
      throw new IllegalArgumentException("Product name must not be null or blank");
    }
    this.value = value;
  }
}
