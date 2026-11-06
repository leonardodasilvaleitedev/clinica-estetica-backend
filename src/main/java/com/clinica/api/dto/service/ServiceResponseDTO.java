package com.clinica.api.dto.service;

import com.clinica.api.domain.entity.Service;

import java.math.BigDecimal;

public record ServiceResponseDTO(
        Long id,
        String name,
        String description,
        Integer durationMinutes,
        BigDecimal price,
        Boolean active
) {
    /**
     * Construtor que mapeia os atributos da entidade Service
     * diretamente para os campos do DTO de resposta.
     */
    public ServiceResponseDTO(Service service) {
        this(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getDurationMinutes(),
                service.getPrice(),
                service.getActive()
        );
    }
}
