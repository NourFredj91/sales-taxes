package com.itsf.sales.taxes.adapter.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_line")
public class OrderLineEntity {

  @EmbeddedId
  private OrderLineId id;

  @Column(nullable = false, precision = 12, scale = 2)
  private int quantity;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal unitPriceHt;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal unitTax;
}
