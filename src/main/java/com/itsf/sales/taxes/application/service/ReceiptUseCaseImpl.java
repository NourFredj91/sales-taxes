package com.itsf.sales.taxes.application.service;

import com.itsf.sales.taxes.domain.model.Product;
import com.itsf.sales.taxes.domain.model.Purchase;
import com.itsf.sales.taxes.domain.port.in.ReceiptUseCase;
import com.itsf.sales.taxes.domain.port.out.OrderLinePort;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class ReceiptUseCaseImpl implements ReceiptUseCase {

  private final OrderLinePort orderLinePort;

  @Override
  public byte[] getReceipt(Purchase purchase) {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      Document doc = new Document();
      PdfWriter.getInstance(doc, baos);
      doc.open();

      doc.add(new Paragraph("Purchase Receipt"));
      doc.add(new Paragraph(" "));
      doc.add(new Paragraph("Products:"));
      for (Product product : purchase.getProducts()) {
        orderLinePort.findOrderLine(purchase.getId(), product.getId())
          .ifPresent(orderLine -> doc.add(new Paragraph(orderLine.getQuantity().getValue() + " " + product.getName().getValue()
            + ": " + product.getPriceTtc().getValue())));
      }

      doc.add(new Paragraph(" "));
      doc.add(new Paragraph("Sales Taxes: " + purchase.getTotalTax().getValue() + " Total: " + purchase.getTotalPrice().getValue()));

      doc.close();
      return baos.toByteArray();
    } catch (Exception e) {
      throw new IllegalStateException("PDF generation failed", e);
    }
  }
}
