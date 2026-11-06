package com.clinica.api.controller;

import com.clinica.api.dto.finance.FinancialSummaryDTO;
import com.clinica.api.dto.finance.TransactionRequestDTO;
import com.clinica.api.dto.finance.TransactionResponseDTO;
import com.clinica.api.service.FinancialTransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finacial-transactions")
public class FinancialTransactionController {

    private final FinancialTransactionService financialTransactionService;

    public FinancialTransactionController(FinancialTransactionService financialTransactionService) {
        this.financialTransactionService = financialTransactionService;
    }

    /**
     * Endpoint POST para registar uma nova transação financeira (receita ou despesa).
     */
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@RequestBody @Valid TransactionRequestDTO dto) {
        TransactionResponseDTO createdTransaction = financialTransactionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    /**
     * Endpoint GET para listar todas as transações financeiras registadas.
     */
    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> findAll() {
        List<TransactionResponseDTO> transactions = financialTransactionService.findAll();
        return ResponseEntity.ok(transactions);
    }

    /**
     * Endpoint GET para consultar uma transação financeira específica através do seu ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> findById(@PathVariable Long id) {
        TransactionResponseDTO transaction = financialTransactionService.findById(id);
        return ResponseEntity.ok(transaction);
    }

    /**
     * Endpoint GET para obter o resumo finance geral (balanço de receitas, despesas e saldo total).
     */
    @GetMapping("/resumo")
    public ResponseEntity<FinancialSummaryDTO> getFinancialSummary() {
        FinancialSummaryDTO summary = financialTransactionService.getFinancialSummary();
        return ResponseEntity.ok(summary);
    }
}
