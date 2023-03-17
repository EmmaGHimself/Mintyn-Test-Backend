package com.mintyn.test.service.impl;

import com.mintyn.test.dto.ProductDto;
import com.mintyn.test.exception.ResourceNotFoundException;
import com.mintyn.test.model.Product;
import com.mintyn.test.repository.ProductRepository;
import com.mintyn.test.service.ProductService;
import com.mintyn.test.validation.ProductDtoValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDtoValidator productDtoValidator;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            ProductDtoValidator productDtoValidator) {
        this.productRepository = productRepository;
        this.productDtoValidator = productDtoValidator;
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = this.productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return new ProductDto(product);
    }

    @Override
    public List<ProductDto> findAll() {
        return this.productRepository
                .findAll()
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        productDtoValidator.validate(productDto);
        Product product = this.dtoToEntity(productDto);
        Product savedProduct = this.productRepository.save(product);
        return new ProductDto(savedProduct);
    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public void updateProductQuantity(Long id, int quantity) {
        Product existingProduct = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        existingProduct.setStock(existingProduct.getStock() - quantity);

        this.productRepository.save(existingProduct);
    }

    @Override
    public List<Product> findByStockGreaterThan(int stock) {
        return this.productRepository.findByStockGreaterThan(stock);
    }

    private Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
