package com.clinica.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "supply_usages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento N para 1 com o funcionário que registrou ou utilizou o supply
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // Relacionamento N para 1 com o supply que foi consumido
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "supply_id")
    private Supply supply;

    @Column(name = "quantity_used", nullable = false)
    private BigDecimal quantityUsed;

    @Builder.Default
    @Column(name = "used_at",nullable = false)
    private LocalDateTime usedAt = LocalDateTime.now();

    @Column(name = "notes")
    private String notes;

}