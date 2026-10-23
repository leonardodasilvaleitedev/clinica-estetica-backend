package com.clinica.api.dto.servicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ServicoRequestDTO(

        // Nome obrigatório do serviço ou procedimento estético
        @NotBlank(message = "O nome do serviço é obrigatório")
        String nome,

        // Descrição opcional detalhando o serviço
        String descricao,

        // Duração obrigatória em minutos, devendo ser estritamente maior que zero
        @NotNull(message = "A duração em minutos é obrigatória")
        @Positive(message = "A duração deve ser maior que zero")
        Integer duracaoMinutos,

        // Preço base obrigatório, devendo ser superior a zero
        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal preco,

        Boolean ativo
) {}