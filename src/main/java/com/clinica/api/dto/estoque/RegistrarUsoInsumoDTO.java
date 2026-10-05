package com.clinica.api.dto.estoque;

import java.math.BigDecimal;

public record RegistrarUsoInsumoDTO(
        Long funcionarioId,
        Long insumoId,
        BigDecimal quantidadeUsada,
        String observacao
) {}
