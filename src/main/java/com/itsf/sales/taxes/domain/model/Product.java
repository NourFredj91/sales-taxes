package com.itsf.sales.taxes.domain.model;

import com.itsf.sales.taxes.domain.model.enumeration.ProductType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder(toBuilder = true)
public class Product {

  private Long id;
  private Name name;
  private Price priceHt;
  private PriceTtc priceTtc;
  private TaxRate tax;
  private ProductType productType;
  private boolean imported;
  private Quantity quantity;
}
