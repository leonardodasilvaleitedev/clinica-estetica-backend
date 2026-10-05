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

    @ManyToOne(optional = false)
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;

    @Column(nullable = false)
    private BigDecimal quantidadeUsada;

    @Column(nullable = false)
    private LocalDateTime dataUso;

    private String observacao;

    public UsoInsumo() {
        this.dataUso = LocalDateTime.now();
    }

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