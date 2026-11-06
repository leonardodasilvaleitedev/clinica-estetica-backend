package com.clinica.api.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record ClientRequestDTO(

        // Nome obrigatório para o cadastro do client
        @NotBlank(message = "O nome é obrigatório")
        String name,

        // CPF obrigatório, validado por expressão regular para garantir exatamente 11 dígitos
        @NotBlank(message = "O CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        String cpf,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O e-mail deve ter um formato válido")
        String email,

        // Telefone de contato obrigatório
        @NotBlank(message = "O telefone é obrigatório")
        String phone,

        @NotNull(message = "A data de nascimento é obrigatória")
        LocalDate birthDate,

        // Sexo opcional
        String gender,

        @NotBlank(message = "As observações médicas são obrigatórias")
        String medicalNotes
) {}