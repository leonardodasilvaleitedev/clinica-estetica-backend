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