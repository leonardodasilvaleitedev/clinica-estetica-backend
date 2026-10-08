package com.clinica.api.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record ClienteRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        String cpf,

        String email,

        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        LocalDate dataNascimento,

        String sexo,

        String observacoesMedicas // <--- Adicionado para suportar observações médicas
) {}