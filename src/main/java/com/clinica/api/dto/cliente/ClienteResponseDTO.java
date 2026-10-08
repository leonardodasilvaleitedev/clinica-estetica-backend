package com.clinica.api.dto.cliente;

import com.clinica.api.domain.model.Cliente;
import java.time.LocalDate;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        LocalDate dataNascimento,
        String sexo,
        String observacoesMedicas,
        Boolean ativo
) {
    // Construtor auxiliar para mapear diretamente da Entidade
    public ClienteResponseDTO(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getDataNascimento(),
                cliente.getSexo(),
                cliente.getObservacoesMedicas(),
                cliente.getAtivo()
        );
    }
}