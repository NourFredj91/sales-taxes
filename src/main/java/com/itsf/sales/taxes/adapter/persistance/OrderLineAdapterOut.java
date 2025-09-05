package com.itsf.sales.taxes.adapter.persistance;

import com.itsf.sales.taxes.adapter.persistance.entity.OrderLineId;
import com.itsf.sales.taxes.adapter.persistance.mapper.OrderLineEntityMapper;
import com.itsf.sales.taxes.adapter.persistance.repository.OrderLineRepository;
import com.itsf.sales.taxes.domain.model.OrderLine;
import com.itsf.sales.taxes.domain.port.out.OrderLinePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderLineAdapterOut implements OrderLinePort {

  private final OrderLineRepository orderLineRepository;
  private final OrderLineEntityMapper orderLineEntityMapper;

  @Override
  public Optional<OrderLine> findOrderLine(long idPurchase, long idProduct) {
    OrderLineId orderLineId = new OrderLineId();
    orderLineId.setProductId(idProduct);
    orderLineId.setPurchaseId(idPurchase);
    return orderLineRepository.findById(orderLineId)
      .map(orderLineEntityMapper::fromEntityToBusiness);
  }

  @Override
  public void saveOrderLine(OrderLine orderLine) {
    orderLineRepository.save(orderLineEntityMapper.fromBusinessToEntity(orderLine));
  }

  @Override
  public void deleteOrderLine(final OrderLine orderLine) {
    orderLineRepository.delete(orderLineEntityMapper.fromBusinessToEntity(orderLine));
  }
}
