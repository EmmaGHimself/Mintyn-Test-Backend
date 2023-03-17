package com.mintyn.test.service;

import com.mintyn.test.dto.OrderDto;
import com.mintyn.test.model.OrderReport;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    OrderDto findById(Long id);

    List<OrderDto> findAll();

    OrderDto save(OrderDto orderDto);

    void deleteById(Long id);

    List<OrderReport> getOrderReport(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
