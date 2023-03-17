package com.mintyn.test.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OrderReport {
    private LocalDate date;
    private long totalOrders;
    private BigDecimal totalOrderAmount;

    public OrderReport(LocalDate date, long totalOrders, BigDecimal totalOrderAmount) {
        this.date = date;
        this.totalOrders = totalOrders;
        this.totalOrderAmount = totalOrderAmount;
    }
}
