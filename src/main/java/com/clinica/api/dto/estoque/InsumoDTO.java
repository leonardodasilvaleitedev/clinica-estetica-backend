package com.clinica.api.dto.estoque;

import java.math.BigDecimal;

public record InsumoDTO(
        Long id,
        String nome,
        String marca,
        String unidadeMedida,
        BigDecimal quantidadeEstoque,
        BigDecimal estoqueMinimo
) {}
