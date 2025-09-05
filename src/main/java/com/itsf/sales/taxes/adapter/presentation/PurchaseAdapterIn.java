package com.itsf.sales.taxes.adapter.presentation;


import com.itsf.sales.taxes.adapter.presentation.dto.PurchaseDto;
import com.itsf.sales.taxes.adapter.presentation.mapper.PurchaseDtoMapper;
import com.itsf.sales.taxes.domain.model.Purchase;
import com.itsf.sales.taxes.domain.port.in.PurchaseUseCase;
import com.itsf.sales.taxes.domain.port.in.ReceiptUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseAdapterIn {

  private final PurchaseDtoMapper purchaseDtoMapper;
  private final PurchaseUseCase purchaseUseCase;
  private final ReceiptUseCase receiptUseCase;

  public PurchaseDto getPurchaseDetails(final long idPurchase) {
    return purchaseDtoMapper.fromBusinessToDto(purchaseUseCase.getPurchase(idPurchase));
  }

  public PurchaseDto createPurchase() {
    return purchaseDtoMapper.fromBusinessToDto(purchaseUseCase.createPurchase());
  }

  public PurchaseDto addProductToPurchase(final long idPurchase, final long idProduct) {
    return purchaseDtoMapper.fromBusinessToDto(purchaseUseCase.addProductToPurchase(idPurchase, idProduct));
  }

  public PurchaseDto removeProductFromPurchase(final long idPurchase, final long idProduct) {
    return purchaseDtoMapper.fromBusinessToDto(purchaseUseCase.removeProductFromPurchase(idPurchase, idProduct));
  }

  public void deletePurchase(final long idPurchase) {
    purchaseUseCase.deletePurchase(idPurchase);
  }

  public byte[] download(final long idPurchase) {
    Purchase purchase = purchaseUseCase.getPurchase(idPurchase);
    return receiptUseCase.getReceipt(purchase);
  }
}
