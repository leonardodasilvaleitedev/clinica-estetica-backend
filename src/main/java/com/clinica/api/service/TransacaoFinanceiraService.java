package com.clinica.api.service;

import com.clinica.api.domain.model.TransacaoFinanceira;
import com.clinica.api.domain.enums.TipoTransacao;
import com.clinica.api.dto.financeiro.ResumoFinanceiroDTO;
import com.clinica.api.dto.financeiro.TransacaoRequestDTO;
import com.clinica.api.dto.financeiro.TransacaoResponseDTO;
import com.clinica.api.repository.TransacaoFinanceiraRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoFinanceiraService {

    private final TransacaoFinanceiraRepository repository;

    public TransacaoFinanceiraService(TransacaoFinanceiraRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TransacaoResponseDTO criar(TransacaoRequestDTO dto) {
        TransacaoFinanceira transacao = TransacaoFinanceira.builder()
                .descricao(dto.getDescricao())
                .valor(dto.getValor())
                .tipo(dto.getTipo())
                .dataTransacao(dto.getDataTransacao())
                .formaPagamento(dto.getFormaPagamento())
                .dataPagamento(dto.getDataPagamento())
                .agendamentoId(dto.getAgendamentoId())
                .build();

        TransacaoFinanceira salva = repository.save(transacao);
        return TransacaoResponseDTO.fromEntity(salva);
    }

    public List<TransacaoResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(TransacaoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public TransacaoResponseDTO buscarPorId(Long id) {
        TransacaoFinanceira transacao = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação financeira não encontrada com o ID: " + id));
        return TransacaoResponseDTO.fromEntity(transacao);
    }

    public ResumoFinanceiroDTO obterResumoFinanceiro() {
        List<TransacaoFinanceira> transacoes = repository.findAll();

        BigDecimal totalReceitas = transacoes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
                .map(TransacaoFinanceira::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDespesas = transacoes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
                .map(TransacaoFinanceira::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldoTotal = totalReceitas.subtract(totalDespesas);

        return ResumoFinanceiroDTO.builder()
                .totalReceitas(totalReceitas)
                .totalDespesas(totalDespesas)
                .saldoTotal(saldoTotal)
                .build();
    }
}
