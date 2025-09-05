package com.itsf.sales.taxes.domain.port.in;

import com.itsf.sales.taxes.domain.model.Purchase;

public interface PurchaseUseCase {

  Purchase getPurchase(long idPurchase);

  Purchase createPurchase();

  Purchase addProductToPurchase(long idPurchase, long idProduct);

  Purchase removeProductFromPurchase(long idPurchase, long idProduct);

  void deletePurchase(long idPurchase);
}
