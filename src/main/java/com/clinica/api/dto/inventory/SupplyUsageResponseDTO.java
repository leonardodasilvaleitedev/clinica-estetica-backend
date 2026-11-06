package com.clinica.api.dto.inventory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SupplyUsageResponseDTO(
        Long id,
        String employeeName,
        String supplyName,
        BigDecimal quantityUsed,
        LocalDateTime usageDate,
        String notes
) {}
