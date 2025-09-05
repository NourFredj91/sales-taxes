package com.itsf.sales.taxes.domain.port.out;

import com.itsf.sales.taxes.domain.model.OrderLine;

import java.util.Optional;

public interface OrderLinePort {

  Optional<OrderLine> findOrderLine(long idPurchase, long idProduct);

  void saveOrderLine(OrderLine orderLine);

  void deleteOrderLine(OrderLine orderLine);
}
