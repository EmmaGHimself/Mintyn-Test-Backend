package com.mintyn.test.controller;

import com.mintyn.test.dto.ProductDto;
import com.mintyn.test.model.Product;
import com.mintyn.test.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{id}")
    public ProductDto findById(@PathVariable Long id) {
        return this.productService.findById(id);
    }

    @GetMapping
    public List<ProductDto> findAll() {
        return this.productService.findAll();
    }

    @GetMapping("available")
    public List<Product> findByQuantityGreaterThan(@RequestParam int stock) {
        return this.productService.findByStockGreaterThan(stock);
    }

    @PostMapping
    public ProductDto save(@RequestBody ProductDto productDto) {
        productDto.setId(null);
        return this.productService.save(productDto);
    }

    @PutMapping("{id}")
    public ProductDto update(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ProductDto existingProduct = findById(id);
        if (productDto.getName() != null) {
            existingProduct.setName(productDto.getName());
        } else {
            existingProduct.setName(existingProduct.getName());
        }
        if (productDto.getPrice() != null) {
            existingProduct.setPrice(productDto.getPrice());
        } else {
            existingProduct.setPrice(existingProduct.getPrice());
        }
        if (productDto.getStock() != 0) {
            existingProduct.setStock(productDto.getStock());
        } else {
            existingProduct.setStock(existingProduct.getStock());
        }
        if (productDto.getDescription() != null) {
            existingProduct.setDescription(productDto.getDescription());
        } else {
            existingProduct.setDescription(existingProduct.getDescription());
        }
        if (productDto.getCode() != null) {
            existingProduct.setCode(productDto.getCode());
        } else {
            existingProduct.setCode(existingProduct.getCode());
        }
        if (productDto.getMeasurementUnit() != null) {
            existingProduct.setMeasurementUnit(productDto.getMeasurementUnit());
        } else {
            existingProduct.setMeasurementUnit(existingProduct.getMeasurementUnit());
        }
        return this.productService.save(existingProduct);
    }

    @PutMapping("{id}/price")
    public ProductDto updatePrice(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ProductDto existingProduct = findById(id);
        existingProduct.setPrice(productDto.getPrice());
        return this.productService.save(existingProduct);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        this.productService.deleteById(id);
    }



}
