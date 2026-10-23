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

    // Injeção de dependência do repositório de transações financeiras via construtor
    public TransacaoFinanceiraService(TransacaoFinanceiraRepository repository) {
        this.repository = repository;
    }

    /**
     * Cria uma nova transação financeira utilizando o padrão Builder,
     * salvando no banco de dados e retornando o DTO de resposta mapeado a partir da entidade.
     */
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

    /**
     * Lista todas as transações financeiras cadastradas, convertendo cada entidade
     * em seu respectivo DTO de resposta através de Streams.
     */
    public List<TransacaoResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(TransacaoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma transação financeira específica pelo ID, lançando uma exceção
     * caso o registro não seja encontrado no banco de dados.
     */
    public TransacaoResponseDTO buscarPorId(Long id) {
        TransacaoFinanceira transacao = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação financeira não encontrada com o ID: " + id));
        return TransacaoResponseDTO.fromEntity(transacao);
    }

    /**
     * Calcula o resumo financeiro geral do sistema filtrando receitas e despesas,
     * somando os valores através de reduce e calculando o saldo total resultante.
     */
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
