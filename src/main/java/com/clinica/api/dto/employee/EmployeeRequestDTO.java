package com.clinica.api.dto.employee;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record EmployeeRequestDTO(

        // Nome obrigatório do funcionário
        @NotBlank(message = "O nome é obrigatório")
        String name,

        // CPF obrigatório para identificação
        @NotBlank(message = "O CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        String cpf,

        // E-mail obrigatório com validação de formato válido
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        // Telefone de contato obrigatório
        @NotBlank(message = "O telefone é obrigatório")
        String phone,

        // Senha obrigatória para acesso ao sistema
        @NotBlank(message = "A senha é obrigatória")
        String password,

        // Cargo obrigatório do funcionário
        @NotBlank(message = "O cargo é obrigatório")
        String role,

        // Percentual de comissão obrigatório (não pode ser negativo)
        @NotNull(message = "O percentual de comissão é obrigatório")
        @PositiveOrZero(message = "A comissão não pode ser negativa")
        BigDecimal commissionPercentage,

        // Status opcional para definir se o funcionário está ativo
        Boolean active
) {}