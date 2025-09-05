package com.itsf.sales.taxes.adapter.presentation.controller;

import com.itsf.sales.taxes.adapter.presentation.ProductAdapterIn;
import com.itsf.sales.taxes.adapter.presentation.dto.ProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final ProductAdapterIn productAdapterIn;

  @GetMapping
  public List<ProductDto> getAllProducts() {
    return productAdapterIn.getAllProducts();
  }

  @GetMapping(value = "/{idProduct}")
  public ProductDto getProduct(@PathVariable long idProduct) {
    return productAdapterIn.getProduct(idProduct);
  }

  @PostMapping
  public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto) {
    return new ResponseEntity<>(productAdapterIn.addProduct(productDto), HttpStatus.CREATED);
  }

  @PutMapping("/{idProduct}")
  ProductDto updateProduct(@PathVariable long idProduct, @Valid @RequestBody ProductDto productDto) {
    return productAdapterIn.updateProduct(idProduct, productDto);
  }

  @DeleteMapping("/{idProduct}")
  public void deleteProduct(@PathVariable long idProduct) {
    productAdapterIn.deleteProduct(idProduct);
  }
}
