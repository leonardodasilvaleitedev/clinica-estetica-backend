package com.clinica.api.dto.employee;

import com.clinica.api.domain.entity.Employee;

import java.math.BigDecimal;

public record EmployeeResponseDTO(
        Long id,
        String name,
        String cpf,
        String email,
        String phone,
        String role,
        BigDecimal commissionPercentage,
        Boolean active
) {
    /**
     * Construtor que mapeia os atributos da entidade Employee para o DTO,
     * convertendo o role (Enum) em String de forma segura (null-safety).
     */
    public EmployeeResponseDTO(Employee employee) {
        this(
                employee.getId(),
                employee.getName(),
                employee.getCpf(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getRole() != null ? employee.getRole().name() : null,
                employee.getCommissionPercentage(),
                employee.getActive()
        );
    }
}