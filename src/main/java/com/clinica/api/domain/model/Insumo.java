package com.clinica.api.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "insumos")
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String marca;


    @Column(nullable = false)
    private String unidadeMedida; // ex: ML, GRAMAS, UNIDADE

    @Column(nullable = false)
    private BigDecimal quantidadeEstoque;

    @Column(nullable = false)
    private BigDecimal estoqueMinimo;


    public Insumo() {}

    public Insumo (String nome, String marca, String unidadeMedida, BigDecimal quantidadeEstoque, BigDecimal estoqueMinimo) {
        this.nome = nome;
        this.marca = marca;
        this.unidadeMedida = unidadeMedida;
        this.quantidadeEstoque = quantidadeEstoque;
        this.estoqueMinimo = estoqueMinimo;
    }

    public void abaterEstoque(BigDecimal quantidade) {
        if (this.quantidadeEstoque.compareTo(quantidade) < 0) {
            throw  new IllegalArgumentException(" Quantidade insuficiente em estoque!");
        }
        this.quantidadeEstoque = this.quantidadeEstoque.subtract(quantidade);
    }

    //Getters e Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getMarca() {return marca;}
    public void setMarca(String marca) {this.marca = marca;}

    public String getUnidadeMedida() {return unidadeMedida;}
    public void setUnidadeMedida(String unidadeMedida) {this.unidadeMedida = unidadeMedida;}

    public BigDecimal getQuantidadeEstoque() {return quantidadeEstoque;}
    public void setQuantidadeEstoque(BigDecimal quantidadeEstoque) {this.quantidadeEstoque = quantidadeEstoque;}

    public BigDecimal getEstoqueMinimo() {return estoqueMinimo;}
    public void setEstoqueMinimo(BigDecimal estoqueMinimo) {this.estoqueMinimo = estoqueMinimo;}

}
