package com.itsf.sales.taxes.adapter.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsf.sales.taxes.adapter.presentation.PurchaseAdapterIn;
import com.itsf.sales.taxes.adapter.presentation.dto.ProductDto;
import com.itsf.sales.taxes.adapter.presentation.dto.PurchaseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.itsf.sales.taxes.domain.model.enumeration.ProductType.FOOD;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PurchaseControllerIt {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  PurchaseAdapterIn purchaseAdapterIn;

  @Test
  void getPurchaseDetails() throws Exception {
    // GIVEN
    ProductDto productDto = createProductDto();
    PurchaseDto purchaseDto = createPurchaseDto().products(singletonList(productDto)).build();
    when(purchaseAdapterIn.getPurchaseDetails(1L)).thenReturn(purchaseDto);

    // WHEN - THEN
    mockMvc.perform(get("/purchases/1"))
      .andExpect(status().isOk())
      // purchase details
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.totalTax").value(50.59))
      .andExpect(jsonPath("$.totalPrice").value(65.39))
      .andExpect(jsonPath("$.products", hasSize(1)))
      // product details
      .andExpect(jsonPath("$.products[0].id").value(3))
      .andExpect(jsonPath("$.products[0].name").value("Chocolate"))
      .andExpect(jsonPath("$.products[0].priceHt").value(42.55))
      .andExpect(jsonPath("$.products[0].priceTtc").value(43.75))
      .andExpect(jsonPath("$.products[0].tax").value(5.25))
      .andExpect(jsonPath("$.products[0].productType").value("FOOD"))
      .andExpect(jsonPath("$.products[0].imported").value(true))
      .andExpect(jsonPath("$.products[0].quantity").value(4));
  }

  @Test
  void createPurchase() throws Exception {
    // GIVEN
    PurchaseDto purchaseDto = createPurchaseDto().build();
    when(purchaseAdapterIn.createPurchase()).thenReturn(purchaseDto);

    // WHEN - THEN
    mockMvc.perform(post("/purchases"))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.totalTax").value(50.59))
      .andExpect(jsonPath("$.totalPrice").value(65.39));
  }

  @Test
  void addProductToPurchase() throws Exception {
    // GIVEN
    ProductDto productDto = createProductDto();
    PurchaseDto purchaseDto = createPurchaseDto().products(singletonList(productDto)).build();
    when(purchaseAdapterIn.addProductToPurchase(1L, 3L)).thenReturn(purchaseDto);

    // WHEN - THEN
    mockMvc.perform(put("/purchases/{idPurchase}/add/products/{idProduct}", 1L, 3L))
      .andExpect(status().isOk())
      // purchase details
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.totalTax").value(50.59))
      .andExpect(jsonPath("$.totalPrice").value(65.39))
      .andExpect(jsonPath("$.products", hasSize(1)))
      // product details
      .andExpect(jsonPath("$.products[0].id").value(3))
      .andExpect(jsonPath("$.products[0].name").value("Chocolate"))
      .andExpect(jsonPath("$.products[0].priceHt").value(42.55))
      .andExpect(jsonPath("$.products[0].priceTtc").value(43.75))
      .andExpect(jsonPath("$.products[0].tax").value(5.25))
      .andExpect(jsonPath("$.products[0].productType").value("FOOD"))
      .andExpect(jsonPath("$.products[0].imported").value(true))
      .andExpect(jsonPath("$.products[0].quantity").value(4));
  }

  @Test
  void removeProductFromPurchase() throws Exception {
    // GIVEN
    PurchaseDto purchaseDto = createPurchaseDto().build();
    when(purchaseAdapterIn.removeProductFromPurchase(1L, 3L)).thenReturn(purchaseDto);

    // WHEN - THEN
    mockMvc.perform(put("/purchases/{idPurchase}/remove/products/{idProduct}", 1L, 3L))
      .andExpect(status().isOk())
      // purchase details
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.totalTax").value(50.59))
      .andExpect(jsonPath("$.totalPrice").value(65.39))
      .andExpect(jsonPath("$.products", hasSize(0)));
  }

  @Test
  void deletePurchase() throws Exception {
    // WHEN - THEN
    mockMvc.perform(delete("/purchases/{idPurchase}", 1L))
      .andExpect(status().isOk());
  }

  @Test
  void download() throws Exception {
    // GIVEN
    ProductDto product = createProductDto();
    PurchaseDto purchase = createPurchaseDto()
      .products(singletonList(product))
      .build();
    when(purchaseAdapterIn.getPurchaseDetails(1L)).thenReturn(purchase);

    // WHEN - THEN
    mockMvc.perform(get("/purchases/{idPurchase}/download", 1L)
        .accept(MediaType.APPLICATION_PDF))
      // THEN (HTTP + headers)
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PDF))
      .andExpect(header().string(
        HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"purchase-1.pdf\""))
      .andReturn();
  }

  private static PurchaseDto.PurchaseDtoBuilder createPurchaseDto() {
    return PurchaseDto.builder()
      .id(1)
      .totalTax(new BigDecimal("50.59"))
      .totalPrice(new BigDecimal("65.39"))
      .products(emptyList());
  }

  private static ProductDto createProductDto() {
    return ProductDto.builder()
      .id(3L)
      .name("Chocolate")
      .priceHt(new BigDecimal("42.55"))
      .priceTtc(new BigDecimal("43.75"))
      .tax(new BigDecimal("5.25"))
      .productType(FOOD)
      .imported(true)
      .quantity(4)
      .build();
  }
}