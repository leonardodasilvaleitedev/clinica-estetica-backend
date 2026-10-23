package com.clinica.api.dto.funcionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

public record FuncionarioRequestDTO(

        // Nome obrigatório do funcionário
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        // CPF obrigatório para identificação
        @NotBlank(message = "O CPF é obrigatório")
        String cpf,

        // E-mail obrigatório com validação de formato válido
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        // Telefone de contato obrigatório
        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        // Senha obrigatória para acesso ao sistema
        @NotBlank(message = "A senha é obrigatória")
        String senha,

        // Cargo obrigatório do funcionário
        @NotBlank(message = "O cargo é obrigatório")
        String cargo,

        // Percentual de comissão obrigatório (não pode ser negativo)
        @NotNull(message = "O percentual de comissão é obrigatório")
        @PositiveOrZero(message = "A comissão não pode ser negativa")
        BigDecimal percentualComissao,

        // Status opcional para definir se o funcionário está ativo
        Boolean ativo
) {}