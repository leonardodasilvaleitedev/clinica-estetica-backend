package com.clinica.api.dto.inventory;

import java.math.BigDecimal;

public record SupplyDTO(
        Long id,
        String name,
        String brand,
        String unitOfMeasure,
        BigDecimal stockQuantity,
        BigDecimal minimumStock
) {}
