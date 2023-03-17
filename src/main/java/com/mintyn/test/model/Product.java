package com.mintyn.test.model;

import com.mintyn.test.enums.MeasurementUnit;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "stock")
    private int stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_unit")
    private MeasurementUnit measurementUnit;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column()
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
