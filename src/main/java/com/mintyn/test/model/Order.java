package com.mintyn.test.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Master entity for massive ware import/export
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "totalAmount")
    private BigDecimal totalAmount;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate date;

    /**
     * Product that is purchase
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
