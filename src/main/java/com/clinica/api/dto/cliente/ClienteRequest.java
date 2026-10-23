package com.clinica.api.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record ClienteRequest(

        // Nome obrigatório para o cadastro do cliente
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        // CPF obrigatório, validado por expressão regular para garantir exatamente 11 dígitos
        @NotBlank(message = "O CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        String cpf,

        // E-mail opcional do cliente
        String email,

        // Telefone de contato obrigatório
        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        // Data de nascimento opcional
        LocalDate dataNascimento,

        // Sexo opcional
        String sexo,

        // Campo opcional para observações médicas e restrições de saúde do cliente
        String observacoesMedicas
) {}