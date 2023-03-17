package com.mintyn.test.service;

import com.mintyn.test.dto.ProductDto;
import com.mintyn.test.model.Product;

import java.util.List;

public interface ProductService {

    ProductDto findById(Long id);

    List<ProductDto> findAll();

    ProductDto save(ProductDto productDto);

    void deleteById(Long id);

    void updateProductQuantity(Long id, int stock);

    List<Product> findByStockGreaterThan(int stock);
}
