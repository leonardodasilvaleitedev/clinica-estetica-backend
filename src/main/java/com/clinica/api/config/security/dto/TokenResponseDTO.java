package com.clinica.api.config.security.dto;

public record TokenResponseDTO(
        String token,
        String tipo,
        String nome,
        String cargo
) {}
