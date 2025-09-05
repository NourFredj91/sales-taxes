package com.itsf.sales.taxes.adapter.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsf.sales.taxes.adapter.presentation.ProductAdapterIn;
import com.itsf.sales.taxes.adapter.presentation.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.itsf.sales.taxes.domain.model.enumeration.ProductType.BOOK;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIT {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  ProductAdapterIn productAdapterIn;

  @Test
  void getAllProducts() throws Exception {
    // GIVEN
    ProductDto product = createProductDto();
    when(productAdapterIn.getAllProducts()).thenReturn(singletonList(product));

    // WHEN - THEN
    mockMvc.perform(get("/products"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].name").value("Book"))
      .andExpect(jsonPath("$[0].priceHt").value(new BigDecimal("12.55")))
      .andExpect(jsonPath("$[0].priceTtc").value(new BigDecimal("13.75")))
      .andExpect(jsonPath("$[0].productType").value("BOOK"))
      .andExpect(jsonPath("$[0].imported").value(false))
      .andExpect(jsonPath("$[0].quantity").value(4));
  }

  @Test
  void getProduct() throws Exception {
    // GIVEN
    ProductDto product = createProductDto();
    when(productAdapterIn.getProduct(1)).thenReturn(product);

    // WHEN - THEN
    mockMvc.perform(get("/products/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.name").value("Book"))
      .andExpect(jsonPath("$.priceHt").value(new BigDecimal("12.55")))
      .andExpect(jsonPath("$.priceTtc").value(new BigDecimal("13.75")))
      .andExpect(jsonPath("$.productType").value("BOOK"))
      .andExpect(jsonPath("$.imported").value(false))
      .andExpect(jsonPath("$.quantity").value(4));
  }

  @Test
  void addProduct() throws Exception {
    // GIVEN
    ProductDto created = createProductDto();
    when(productAdapterIn.addProduct(any(ProductDto.class))).thenReturn(created);

    // WHEN - THEN
    mockMvc.perform(post("/products")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(created)))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.name").value("Book"))
      .andExpect(jsonPath("$.priceHt").value(12.55))
      .andExpect(jsonPath("$.priceTtc").value(13.75))
      .andExpect(jsonPath("$.productType").value("BOOK"))
      .andExpect(jsonPath("$.imported").value(false))
      .andExpect(jsonPath("$.quantity").value(4));
  }

  @Test
  void updateProduct() throws Exception {
    // GIVEN
    ProductDto body = createProductDto();
    ProductDto updated = createProductDto();

    when(productAdapterIn.updateProduct(eq(1L), any(ProductDto.class)))
      .thenReturn(updated);

    // WHEN - THEN
    mockMvc.perform(put("/products/{idProduct}", 1L)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body)))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.name").value("Book"))
      .andExpect(jsonPath("$.priceHt").value(12.55))
      .andExpect(jsonPath("$.priceTtc").value(13.75))
      .andExpect(jsonPath("$.productType").value("BOOK"))
      .andExpect(jsonPath("$.imported").value(false))
      .andExpect(jsonPath("$.quantity").value(4));
  }

  @Test
  void deleteProduct() throws Exception {
    // WHEN - THEN
    mockMvc.perform(delete("/products/1"))
      .andExpect(status().isOk());
  }

  private static ProductDto createProductDto() {
    return ProductDto.builder()
      .id(1L)
      .name("Book")
      .priceHt(new BigDecimal("12.55"))
      .priceTtc(new BigDecimal("13.75"))
      .tax(new BigDecimal("1.25"))
      .productType(BOOK)
      .imported(false)
      .quantity(4)
      .build();
  }
}