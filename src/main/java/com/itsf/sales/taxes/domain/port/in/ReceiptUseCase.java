package com.itsf.sales.taxes.domain.port.in;

import com.itsf.sales.taxes.domain.model.Purchase;

public interface ReceiptUseCase {
  byte[] getReceipt(Purchase purchase);
}
