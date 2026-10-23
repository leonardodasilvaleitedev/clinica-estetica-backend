package com.clinica.api.domain.model;

import com.clinica.api.domain.enums.StatusAgendamento;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento N para 1 com Cliente (Carregamento otimizado com LAZY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Relacionamento N para 1 com o Serviço prestado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    // Relacionamento N para 1 com o Funcionário responsável pelo atendimento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim")
    private LocalDateTime dataHoraFim;

    // Valor monetário cobrado (com precisão exata para valores financeiros)
    @Column(name = "valor_cobrado", precision = 10, scale = 2)
    private BigDecimal valorCobrado;


    // Armazena o enum do status como texto na base de dados
    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;

    private String observacao;

    // Regista automaticamente a data e hora de criação do registo
    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

}