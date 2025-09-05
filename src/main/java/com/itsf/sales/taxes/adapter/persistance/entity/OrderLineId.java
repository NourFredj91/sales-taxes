package com.itsf.sales.taxes.adapter.persistance.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class OrderLineId implements Serializable {
  private Long purchaseId;
  private Long productId;
}
