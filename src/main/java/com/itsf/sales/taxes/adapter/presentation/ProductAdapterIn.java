package com.itsf.sales.taxes.adapter.presentation;

import com.itsf.sales.taxes.adapter.presentation.dto.ProductDto;
import com.itsf.sales.taxes.adapter.presentation.mapper.ProductDtoMapper;
import com.itsf.sales.taxes.domain.model.Product;
import com.itsf.sales.taxes.domain.port.in.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAdapterIn {

  private final ProductDtoMapper productDtoMapper;
  private final ProductUseCase productUseCase;

  public List<ProductDto> getAllProducts() {
    return productUseCase.getAllProducts().stream().map(productDtoMapper::fromBusinessToDto).toList();
  }

  public ProductDto getProduct(final long idProduct) {
    return productDtoMapper.fromBusinessToDto(productUseCase.getProduct(idProduct));
  }

  public ProductDto addProduct(final ProductDto productDto) {
    Product product = productDtoMapper.fromDtoToBusiness(productDto);
    return productDtoMapper.fromBusinessToDto(productUseCase.addProduct(product));
  }

  public ProductDto updateProduct(final long productId, final ProductDto productDto) {
    Product product = productDtoMapper.fromDtoToBusiness(productDto);
    return productDtoMapper.fromBusinessToDto(productUseCase.updateProduct(productId, product));
  }

  public void deleteProduct(final long idProduct) {
    productUseCase.deleteProduct(idProduct);
  }
}
