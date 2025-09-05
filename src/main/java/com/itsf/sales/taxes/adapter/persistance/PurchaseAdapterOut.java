package com.itsf.sales.taxes.adapter.persistance;

import com.itsf.sales.taxes.adapter.persistance.entity.PurchaseEntity;
import com.itsf.sales.taxes.adapter.persistance.exception.ResourceNotFoundException;
import com.itsf.sales.taxes.adapter.persistance.mapper.PurchaseEntityMapper;
import com.itsf.sales.taxes.adapter.persistance.repository.OrderLineRepository;
import com.itsf.sales.taxes.adapter.persistance.repository.PurchaseRepository;
import com.itsf.sales.taxes.domain.model.Purchase;
import com.itsf.sales.taxes.domain.port.out.PurchasePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseAdapterOut implements PurchasePort {

  private final PurchaseRepository purchaseRepository;
  private final OrderLineRepository orderLineRepository;
  private final PurchaseEntityMapper purchaseEntityMapper;

  @Override
  public Purchase findPurchaseById(final long idPurchase) {
    return purchaseRepository.findById(idPurchase)
      .map(purchaseEntityMapper::fromEntityToBusiness)
      .orElseThrow(() -> new ResourceNotFoundException("Purchase " + idPurchase + " not found"));
  }

  @Override
  public Purchase savePurchase() {
    PurchaseEntity purchaseEntity = new PurchaseEntity();
    purchaseEntity.setDate(LocalDate.now());
    purchaseEntity.setTotalTax(BigDecimal.ZERO);
    purchaseEntity.setTotalPrice(BigDecimal.ZERO);
    return purchaseEntityMapper.fromEntityToBusiness(purchaseRepository.save(purchaseEntity));
  }

  @Override
  public Purchase savePurchase(Purchase purchase) {
    PurchaseEntity purchaseEntity = purchaseRepository.save(purchaseEntityMapper.fromBusinessToEntity(purchase, LocalDate.now()));
    return purchaseEntityMapper.fromEntityToBusiness(purchaseEntity);
  }

  @Override
  public void delete(long idPurchase) {
    orderLineRepository.deleteAll(orderLineRepository.findAllByIdPurchase(idPurchase));
    purchaseRepository.deleteById(idPurchase);
  }
}
