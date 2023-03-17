package com.mintyn.test.dto;

import com.mintyn.test.model.Order;
import com.mintyn.test.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private String customerName;
    private String customerPhone;
    private int quantity;
    private BigDecimal totalAmount;
    private Long productId;

    public OrderDto(Order order) {
        BeanUtils.copyProperties(order, this, "orderDetails");

        Product product = order.getProduct();
        if (product != null) {
            this.productId = product.getId();
        }
    }

}
