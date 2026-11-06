package com.clinica.api.dto.appointment;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AppointmentRequestDTO(

        // ID obrigatório do client para associar ao agendamento
        @NotNull(message = "O ID do client é obrigatório")
        Long clientId,

        // ID obrigatório do serviço a ser realizado
        @NotNull(message = "O ID do serviço é obrigatório")
        Long serviceId,

        // ID obrigatório do funcionário responsável pelo atendimento
        @NotNull(message = "O ID do funcionário é obrigatório")
        Long employeeId,

        // Data e hora de início obrigatórias para o agendamento
        @NotNull(message = "A data e hora de início são obrigatórias")
        LocalDateTime startDateTime,

        // Opcional: se não for informado, o sistema assume o preço padrão do serviço
        BigDecimal priceCharged,

        // Observações adicionais opcionais sobre o atendimento
        String notes
) {}
