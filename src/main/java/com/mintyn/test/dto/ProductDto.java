package com.mintyn.test.dto;

import com.mintyn.test.enums.MeasurementUnit;
import com.mintyn.test.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String code;
    private BigDecimal price;
    private int stock;
    private String description;
    private MeasurementUnit measurementUnit;

    public ProductDto(Product product) {
        BeanUtils.copyProperties(product, this);
    }
}
