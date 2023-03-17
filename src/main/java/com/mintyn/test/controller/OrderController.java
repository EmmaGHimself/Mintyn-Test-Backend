package com.mintyn.test.controller;

import com.mintyn.test.dto.OrderDto;
import com.mintyn.test.model.OrderReport;
import com.mintyn.test.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("{id}")
    public OrderDto findById(@PathVariable Long id) {
        return this.orderService.findById(id);
    }

    @GetMapping
    public List<OrderDto> findAll() {
        return this.orderService.findAll();
    }

    @PostMapping
    public OrderDto save(@RequestBody OrderDto orderDto) {
        orderDto.setId(null);
        return this.orderService.save(orderDto);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        this.orderService.deleteById(id);
    }

    @GetMapping("report")
    public List<OrderReport> getOrderReport(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return this.orderService.getOrderReport(startDate, endDate);
    }
}
