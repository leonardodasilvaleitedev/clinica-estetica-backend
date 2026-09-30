package com.clinica.api.dto.Agendamento;

import com.clinica.api.domain.model.Agendamento;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Long id,
        Long clienteId,
        String clienteNome,
        Long servicoId,
        String servicoNome,
        Long funcionarioId,
        String funcionarioNome,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        BigDecimal valorCobrado,
        String status,
        String observacao
) {
    public AgendamentoResponseDTO(Agendamento agendamento) {
        this(
                agendamento.getId(),
                agendamento.getCliente() != null ? agendamento.getCliente().getId() : null,
                agendamento.getCliente() != null ? agendamento.getCliente().getNome() : null,
                agendamento.getServico() != null ? agendamento.getServico().getId() : null,
                agendamento.getServico() != null ? agendamento.getServico().getNome() : null,
                agendamento.getFuncionario() != null ? agendamento.getFuncionario().getId() : null,
                agendamento.getFuncionario() != null ? agendamento.getFuncionario().getNome() : null,
                agendamento.getDataHoraInicio(),
                agendamento.getDataHoraFim(),
                agendamento.getValorCobrado(),
                agendamento.getStatus() != null ? agendamento.getStatus().name() : null,
                agendamento.getObservacao()
        );
    }
}