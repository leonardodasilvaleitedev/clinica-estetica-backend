package com.clinica.api.dto.cliente;

import com.clinica.api.domain.model.Cliente;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClienteResponse(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String email,
        String sexo,
        LocalDate dataNascimento,
        LocalDateTime criadoEm
) {
    public ClienteResponse(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getSexo(),
                cliente.getDataNascimento(),
                cliente.getCriadoEm()
        );
    }
}