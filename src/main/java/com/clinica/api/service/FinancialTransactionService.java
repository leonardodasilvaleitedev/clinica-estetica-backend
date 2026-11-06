package com.clinica.api.service;

import com.clinica.api.domain.entity.FinancialTransaction;
import com.clinica.api.domain.enums.TransactionType;
import com.clinica.api.dto.finance.FinancialSummaryDTO;
import com.clinica.api.dto.finance.TransactionRequestDTO;
import com.clinica.api.dto.finance.TransactionResponseDTO;
import com.clinica.api.repository.FinancialTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialTransactionService {

    private final FinancialTransactionRepository repository;

    // Injeção de dependência do repositório de transações financeiras via construtor
    public FinancialTransactionService(FinancialTransactionRepository repository) {
        this.repository = repository;
    }

    /**
     * Cria uma nova transação financeira utilizando o padrão Builder,
     * salvando no banco de dados e retornando o DTO de resposta mapeado a partir da entidade.
     */
    @Transactional
    public TransactionResponseDTO create(TransactionRequestDTO dto) {
        FinancialTransaction transaction = FinancialTransaction.builder()
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .type(dto.getType())
                .transactionDate(dto.getTransactionDate())
                .paymentMethod(dto.getPaymentMethod())
                .paymentDate(dto.getPaymentDate())
                .appointmentId(dto.getAppointmentId())
                .build();

        FinancialTransaction saved = repository.save(transaction);
        return TransactionResponseDTO.fromEntity(saved);
    }

    /**
     * Lista todas as transações financeiras cadastradas, convertendo cada entidade
     * em seu respectivo DTO de resposta através de Streams.
     */
    public List<TransactionResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(TransactionResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma transação financeira específica pelo ID, lançando uma exceção
     * caso o registro não seja encontrado no banco de dados.
     */
    public TransactionResponseDTO findById(Long id) {
        FinancialTransaction transaction = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação financeira não encontrada com o ID: " + id));
        return TransactionResponseDTO.fromEntity(transaction);
    }

    /**
     * Calcula o resumo finance geral do sistema filtrando receitas e despesas,
     * somando os valores através de reduce e calculando o saldo total resultante.
     */
    public FinancialSummaryDTO getFinancialSummary() {
        List<FinancialTransaction> transactions = repository.findAll();

        BigDecimal totalIncome = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .map(FinancialTransaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenses = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .map(FinancialTransaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netBalance = totalIncome.subtract(totalExpenses);

        return FinancialSummaryDTO.builder()
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .netBalance(netBalance)
                .build();
    }
}
