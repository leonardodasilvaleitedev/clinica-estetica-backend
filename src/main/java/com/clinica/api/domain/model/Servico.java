package com.clinica.api.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "servicos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    // Define o campo como TEXT na base de dados para suportar descrições longas
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos;

    // Preço base do serviço com precisão monetária exata (BigDecimal)
    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoBase;

    // Garante que novos serviços nascem ativos por defeito
    @Builder.Default
    @Column(nullable = false)
    private Boolean ativo = true;
}