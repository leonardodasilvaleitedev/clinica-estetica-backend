package com.clinica.api.dto.funcionario;

import com.clinica.api.domain.model.Funcionario;
import java.math.BigDecimal;

public record FuncionarioResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        String cargo,
        BigDecimal percentualComissao,
        Boolean ativo
) {
    /**
     * Construtor que mapeia os atributos da entidade Funcionario para o DTO,
     * convertendo o cargo (Enum) em String de forma segura (null-safety).
     */
    public FuncionarioResponseDTO(Funcionario funcionario) {
        this(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getEmail(),
                funcionario.getTelefone(),
                funcionario.getCargo() != null ? funcionario.getCargo().name() : null,
                funcionario.getPercentualComissao(),
                funcionario.getAtivo()
        );
    }
}