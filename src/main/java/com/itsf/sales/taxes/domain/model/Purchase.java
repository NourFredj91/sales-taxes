package com.itsf.sales.taxes.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
public class Purchase {

  private Long id;
  private List<Product> products;
  private Price totalPrice;
  private TotalTax totalTax;
}
