package com.clinica.api.dto.Agendamento;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AgendamentoRequestDTO(

        // ID obrigatório do cliente para associar ao agendamento
        @NotNull(message = "O ID do cliente é obrigatório")
        Long clienteId,

        // ID obrigatório do serviço a ser realizado
        @NotNull(message = "O ID do serviço é obrigatório")
        Long servicoId,

        // ID obrigatório do funcionário responsável pelo atendimento
        @NotNull(message = "O ID do funcionário é obrigatório")
        Long funcionarioId,

        // Data e hora de início obrigatórias para o agendamento
        @NotNull(message = "A data e hora de início são obrigatórias")
        LocalDateTime dataHoraInicio,

        // Opcional: se não for informado, o sistema assume o preço padrão do serviço
        BigDecimal valorCobrado,

        // Observações adicionais opcionais sobre o atendimento
        String observacao
) {}
