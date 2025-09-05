package com.itsf.sales.taxes.domain.port.out;

import com.itsf.sales.taxes.domain.model.Purchase;


public interface PurchasePort {

  Purchase findPurchaseById(long idPurchase);
  Purchase savePurchase();
  Purchase savePurchase(Purchase purchase);
  void delete(long idPurchase);
}
