package com.itsf.sales.taxes.application.service;

import com.itsf.sales.taxes.domain.model.Name;
import com.itsf.sales.taxes.domain.model.OrderLine;
import com.itsf.sales.taxes.domain.model.Price;
import com.itsf.sales.taxes.domain.model.PriceTtc;
import com.itsf.sales.taxes.domain.model.Product;
import com.itsf.sales.taxes.domain.model.Purchase;
import com.itsf.sales.taxes.domain.model.Quantity;
import com.itsf.sales.taxes.domain.model.TotalTax;
import com.itsf.sales.taxes.domain.port.out.OrderLinePort;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiptUseCaseImplTest {

  @InjectMocks
  private ReceiptUseCaseImpl useCase;

  @Mock
  private OrderLinePort orderLinePort;

  @Test
  void getReceipt() throws Exception {
    // GIVEN
    Product product = Product.builder()
      .id(13L)
      .name(new Name("Chocolate"))
      .priceTtc(new PriceTtc(new BigDecimal("43.75")))
      .build();

    Purchase purchase = Purchase.builder()
      .id(12L)
      .products(singletonList(product))
      .totalTax(new TotalTax(new BigDecimal("50.59")))
      .totalPrice(new Price(new BigDecimal("65.39")))
      .build();

    OrderLine orderLine = OrderLine.builder().quantity(new Quantity(2)).build();
    when(orderLinePort.findOrderLine(12L, 13L)).thenReturn(Optional.of(orderLine));

    // WHEN
    byte[] pdf = useCase.getReceipt(purchase);

    // THEN
    assertThat(pdf).isNotEmpty();

    String text;
    try (PDDocument doc = PDDocument.load(pdf)) {
      text = new PDFTextStripper().getText(doc);
    }

    assertThat(text).contains("Purchase Receipt");
    assertThat(text).contains("2 Chocolate: 43.75");
    assertThat(text).contains("Sales Taxes: 50.59 Total: 65.39");
  }
}