package com.clinica.api.dto.inventory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RegisterSupplyUsageDTO(


        @NotNull(message = "O ID do funcionário é obrigatório")
        Long employeeId,

        @NotNull(message = "O ID do insumo é obrigatório")
        Long supplyId,

        @NotNull(message = "A quantidade usada é obrigatória")
        @Positive(message = "A quantidade usada deve ser maior que zero")
        BigDecimal quantityUsed,

        String notes
) {}
