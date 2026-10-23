package com.clinica.api.dto.financeiro;

import com.clinica.api.domain.model.TransacaoFinanceira;
import com.clinica.api.domain.enums.TipoTransacao;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class TransacaoResponseDTO {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private TipoTransacao tipo;
    private LocalDate dataTransacao;
    private String formaPagamento;
    private LocalDateTime dataPagamento;
    private Long agendamentoId;

    /**
     * Método estático que converte uma entidade TransacaoFinanceira
     * em um TransacaoResponseDTO utilizando o padrão Builder.
     */
    public static TransacaoResponseDTO fromEntity(TransacaoFinanceira transacao) {
        return TransacaoResponseDTO.builder()
                .id(transacao.getId())
                .descricao(transacao.getDescricao())
                .valor(transacao.getValor())
                .tipo(transacao.getTipo())
                .dataTransacao(transacao.getDataTransacao())
                .formaPagamento(transacao.getFormaPagamento())
                .dataPagamento(transacao.getDataPagamento())
                .agendamentoId(transacao.getAgendamentoId())
                .build();
    }
}