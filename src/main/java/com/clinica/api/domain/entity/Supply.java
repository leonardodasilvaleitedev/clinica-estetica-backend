package com.clinica.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "supplies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "brand")
    private String brand;

    // Unidade de medida (ex: ML, GRAMAS, UNIDADE)
    @Column(name = "unit_of_measure", nullable = false)
    private String unitOfMeasure;

    @Column(name = "stock_quantity", nullable = false)
    private BigDecimal stockQuantity;

    @Column(name = "minimum_stock", nullable = false)
    private BigDecimal minimumStock;

    /**
     * Valida se existe quantidade suficiente em estoque e abate o valor informado.
     */
    public void deductStock(BigDecimal quantity) {
        if (this.stockQuantity.compareTo(quantity) < 0) {
            throw new IllegalArgumentException("Insufficient inventory quantity!");
        }
        this.stockQuantity = this.stockQuantity.subtract(quantity);
    }
}

