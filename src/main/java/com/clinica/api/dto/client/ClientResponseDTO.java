package com.clinica.api.dto.client;

import com.clinica.api.domain.entity.Client;

import java.time.LocalDate;

public record ClientResponseDTO(
        Long id,
        String name,
        String cpf,
        String email,
        String phone,
        LocalDate birthDate,
        String gender,
        String medicalNotes,
        Boolean active
) {

    /**
     * Construtor que mapeia os atributos da entidade Client diretamente
     * para os campos do DTO de resposta.
     */
    public ClientResponseDTO(Client client) {
        this(
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getEmail(),
                client.getPhone(),
                client.getBirthDate(),
                client.getGender(),
                client.getMedicalNotes(),
                client.getActive()
        );
    }
}