package com.itsf.sales.taxes.adapter.presentation;

import com.itsf.sales.taxes.adapter.presentation.dto.PurchaseDto;
import com.itsf.sales.taxes.adapter.presentation.mapper.PurchaseDtoMapper;
import com.itsf.sales.taxes.domain.model.Purchase;
import com.itsf.sales.taxes.domain.port.in.PurchaseUseCase;
import com.itsf.sales.taxes.domain.port.in.ReceiptUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseAdapterInTest {

  @InjectMocks
  private PurchaseAdapterIn adapter;

  @Mock
  private PurchaseDtoMapper mapper;
  @Mock
  private PurchaseUseCase useCase;
  @Mock
  private ReceiptUseCase receiptUseCase;

  @Test
  void getPurchaseDetails() {
    // GIVEN
    Purchase purchase = mock(Purchase.class);
    when(useCase.getPurchase(11L)).thenReturn(purchase);

    PurchaseDto purchaseDto = mock(PurchaseDto.class);
    when(mapper.fromBusinessToDto(purchase)).thenReturn(purchaseDto);

    // WHEN
    PurchaseDto result = adapter.getPurchaseDetails(11L);

    // THEN
    assertThat(result).isEqualTo(purchaseDto);
  }

  @Test
  void createPurchase() {
    // GIVEN
    Purchase purchase = mock(Purchase.class);
    when(useCase.createPurchase()).thenReturn(purchase);

    PurchaseDto purchaseDto = mock(PurchaseDto.class);
    when(mapper.fromBusinessToDto(purchase)).thenReturn(purchaseDto);

    // WHEN
    PurchaseDto result = adapter.createPurchase();

    // THEN
    assertThat(result).isEqualTo(purchaseDto);
  }

  @Test
  void addProductToPurchase() {
    // GIVEN
    Purchase purchase = mock(Purchase.class);
    when(useCase.addProductToPurchase(11L, 12)).thenReturn(purchase);

    PurchaseDto purchaseDto = mock(PurchaseDto.class);
    when(mapper.fromBusinessToDto(purchase)).thenReturn(purchaseDto);

    // WHEN
    PurchaseDto result = adapter.addProductToPurchase(11L, 12L);

    // THEN
    assertThat(result).isEqualTo(purchaseDto);
  }

  @Test
  void removeProductFromPurchase() {
    // GIVEN
    Purchase purchase = mock(Purchase.class);
    when(useCase.removeProductFromPurchase(11L, 12)).thenReturn(purchase);

    PurchaseDto purchaseDto = mock(PurchaseDto.class);
    when(mapper.fromBusinessToDto(purchase)).thenReturn(purchaseDto);

    // WHEN
    PurchaseDto result = adapter.removeProductFromPurchase(11L, 12L);

    // THEN
    assertThat(result).isEqualTo(purchaseDto);
  }

  @Test
  void deletePurchase() {
    // WHEN
    adapter.deletePurchase(11L);

    // THEN
    verify(useCase).deletePurchase(11L);
  }

  @Test
  void download() {
    // GIVEN
    Purchase purchase = mock(Purchase.class);
    when(useCase.getPurchase(11L)).thenReturn(purchase);

    byte[] contenu = "contenu".getBytes();
    when(receiptUseCase.getReceipt(purchase)).thenReturn(contenu);

    // WHEN
    byte[] result = adapter.download(11L);

    //THEN
    assertThat(result).isEqualTo(contenu);
  }
}