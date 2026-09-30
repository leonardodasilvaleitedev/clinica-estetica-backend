package com.clinica.api.dto.funcionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

public record FuncionarioRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O CPF é obrigatório")
       // @CPF(message = "CPF inválido")
        String cpf,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        @NotBlank(message = "A senha é obrigatória")
        String senhaHash,

        @NotBlank(message = "O cargo é obrigatório")
        String cargo,

        @NotNull(message = "O percentual de comissão é obrigatório")
        @PositiveOrZero(message = "A comissão não pode ser negativa")
        BigDecimal percentualComissao,

        Boolean ativo
) {}