package com.itsf.sales.taxes.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class OrderLine {
  private OrderLineIdModel id;
  private Quantity quantity;
  private Price unitPriceHt;
  private TaxRate unitTax;
}
