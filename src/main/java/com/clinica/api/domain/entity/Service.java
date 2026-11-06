package com.clinica.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    // Define o campo como TEXT na base de dados para suportar descrições longas
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    // Preço base do serviço com precisão monetária exata (BigDecimal)
    @Column(name = "base_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // Garante que novos serviços nascem ativos por defeito
    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;
}