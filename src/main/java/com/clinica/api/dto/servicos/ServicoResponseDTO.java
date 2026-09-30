package com.clinica.api.dto.servicos;

import com.clinica.api.domain.model.Servico;
import java.math.BigDecimal;

public record ServicoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Integer duracaoMinutos,
        BigDecimal preco,
        Boolean ativo
) {
    public ServicoResponseDTO(Servico servico) {
        this(
                servico.getId(),
                servico.getNome(),
                servico.getDescricao(),
                servico.getDuracaoMinutos(),
                servico.getPrecoBase(),
                servico.getAtivo()
        );
    }
}
