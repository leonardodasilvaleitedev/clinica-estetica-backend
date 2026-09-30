package com.clinica.api.dto.Agendamento;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AgendamentoRequestDTO(
        @NotNull(message = "O ID do cliente é obrigatório")
        Long clienteId,

        @NotNull(message = "O ID do serviço é obrigatório")
        Long servicoId,

        @NotNull(message = "O ID do funcionário é obrigatório")
        Long funcionarioId,

        @NotNull(message = "A data e hora de início são obrigatórias")
        LocalDateTime dataHoraInicio,

        BigDecimal valorCobrado, // Opcional: se não for informado, assume o preço do serviço

        String observacao
) {}
