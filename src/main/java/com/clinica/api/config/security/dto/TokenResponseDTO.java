package com.clinica.api.config.security.dto;

public record TokenResponseDTO(
        String token,
        String type,
        String name,
        String role
) {}
