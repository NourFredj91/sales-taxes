package com.itsf.sales.taxes.application.service;

import com.itsf.sales.taxes.domain.model.PriceTtc;
import com.itsf.sales.taxes.domain.model.Product;
import com.itsf.sales.taxes.domain.model.TaxRate;
import com.itsf.sales.taxes.domain.model.enumeration.ProductType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PriceTtcCalculator {

  private static final BigDecimal BASIC_TAX_RATE = new BigDecimal("0.10");
  private static final BigDecimal IMPORT_TAX_RATE = new BigDecimal("0.05");
  private static final BigDecimal STEP_0_05 = new BigDecimal("0.05");

  public Product calculer(Product product) {
    BigDecimal totalHt = product.getPriceHt().getValue();

    BigDecimal tax = BigDecimal.ZERO;
    if (ProductType.OTHER.equals(product.getProductType())) {
      tax = tax.add(BASIC_TAX_RATE);
    }
    if (product.isImported()) {
      tax = tax.add(IMPORT_TAX_RATE);
    }

    BigDecimal taxBrute = product.getPriceHt().getValue()
      .multiply(tax);

    BigDecimal taxArrondi = roundUpToNearest005(taxBrute);

    BigDecimal priceTtc = totalHt.add(taxArrondi).setScale(2, RoundingMode.HALF_UP);

    return product.toBuilder().priceTtc(new PriceTtc(priceTtc)).tax(new TaxRate(taxArrondi)).build();
  }

  private BigDecimal roundUpToNearest005(BigDecimal value) {
    if (value.signum() == 0) {
      return BigDecimal.ZERO.setScale(2, RoundingMode.UNNECESSARY);
    }
    BigDecimal divided = value.divide(STEP_0_05, 0, RoundingMode.UP);
    return divided.multiply(STEP_0_05).setScale(2, RoundingMode.UNNECESSARY);
  }
}
