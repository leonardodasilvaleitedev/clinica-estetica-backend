package com.clinica.api.dto.estoque;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UsoInsumoResponseDTO(
        Long id,
        String nomeFuncionario,
        String nomeInsumo,
        BigDecimal quantidadeUsada,
        LocalDateTime dataUso,
        String observacao
) {}
