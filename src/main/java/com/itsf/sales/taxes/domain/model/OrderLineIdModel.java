package com.itsf.sales.taxes.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderLineIdModel {
  private long purchaseId;
  private long productId;
}
