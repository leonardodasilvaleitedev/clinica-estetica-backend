package com.clinica.api.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "usos_insumos")

public class UsoInsumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento N para 1 com o funcionário que registrou ou utilizou o insumo
    @ManyToOne(optional = false)
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    // Relacionamento N para 1 com o insumo que foi consumido
    @ManyToOne(optional = false)
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;

    @Column(nullable = false)
    private BigDecimal quantidadeUsada;

    @Column(nullable = false)
    private LocalDateTime dataUso;

    private String observacao;

    // Construtor padrão que inicializa a data do uso automaticamente com o momento atual
    public UsoInsumo() {
        this.dataUso = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Funcionario getFuncionario() {return funcionario;}
    public void setFuncionario(Funcionario funcionario) {this.funcionario = funcionario;}

    public Insumo getInsumo() {return insumo;}
    public void setInsumo(Insumo insumo) {this.insumo = insumo;}

    public BigDecimal getQuantidadeUsada() {return quantidadeUsada;}
    public void setQuantidadeUsada(BigDecimal quantidadeUsada){this.quantidadeUsada = quantidadeUsada;}

    public LocalDateTime getDataUso() {return dataUso;}
    public void setDataUso(LocalDateTime dataUso) {this.dataUso = dataUso;}

    public String getObservacao() {return observacao;}
    public void setObservacao(String observacao) {this.observacao = observacao;}

}