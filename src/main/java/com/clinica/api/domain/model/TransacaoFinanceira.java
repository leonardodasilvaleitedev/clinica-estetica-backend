package com.clinica.api.domain.model;

import com.clinica.api.domain.enums.TipoTransacao;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes_financeiras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransacaoFinanceira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    // Armazena o tipo da transação (ex: receita ou despesa) como texto no banco de dados
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipo;

    @Column(nullable = false)
    private LocalDate dataTransacao;

    @Column(name = "forma_pagamento")
    private String formaPagamento;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    // Referência opcional ao ID do agendamento que originou a transação
    private Long agendamentoId;
}