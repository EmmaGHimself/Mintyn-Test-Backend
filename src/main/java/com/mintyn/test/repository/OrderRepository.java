package com.mintyn.test.repository;

import com.mintyn.test.model.Order;
import com.mintyn.test.model.OrderReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT new com.mintyn.test.model.OrderReport(o.date, COUNT(o.id), SUM(o.totalAmount)) FROM Order o WHERE o.date BETWEEN :startDate AND :endDate GROUP BY o.date")
    List<OrderReport> getOrderReport(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
