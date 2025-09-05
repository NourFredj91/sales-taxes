package com.itsf.sales.taxes.adapter.presentation.controller;


import com.itsf.sales.taxes.adapter.presentation.PurchaseAdapterIn;
import com.itsf.sales.taxes.adapter.presentation.dto.PurchaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchases")
public class PurchaseController {

  private final PurchaseAdapterIn purchaseAdapterIn;

  @GetMapping("/{idPurchase}")
  public PurchaseDto getPurchaseDetails(@PathVariable long idPurchase) {
    return purchaseAdapterIn.getPurchaseDetails(idPurchase);
  }

  @PostMapping
  public ResponseEntity<PurchaseDto> createPurchase() {
    return new ResponseEntity<>(purchaseAdapterIn.createPurchase(), HttpStatus.CREATED);
  }

  @PutMapping("/{idPurchase}/add/products/{idProduct}")
  public PurchaseDto addProductToPurchase(@PathVariable long idPurchase, @PathVariable long idProduct) {
    return purchaseAdapterIn.addProductToPurchase(idPurchase, idProduct);
  }

  @PutMapping("/{idPurchase}/remove/products/{idProduct}")
  public PurchaseDto removeProductFromPurchase(@PathVariable long idPurchase, @PathVariable long idProduct) {
    return purchaseAdapterIn.removeProductFromPurchase(idPurchase, idProduct);
  }

  @DeleteMapping("/{idPurchase}")
  public void deletePurchase(@PathVariable long idPurchase) {
    purchaseAdapterIn.deletePurchase(idPurchase);
  }

  @GetMapping("/{idPurchase}/download")
  public ResponseEntity<byte[]> download(@PathVariable long idPurchase) {
    byte[] pdf = purchaseAdapterIn.download(idPurchase);
    return ResponseEntity.ok()
      .contentType(MediaType.APPLICATION_PDF)
      .header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"purchase-" + idPurchase + ".pdf\"")
      .body(pdf);
  }
}
