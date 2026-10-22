package com.clinica.api.controller;

import com.clinica.api.dto.financeiro.ResumoFinanceiroDTO;
import com.clinica.api.dto.financeiro.TransacaoRequestDTO;
import com.clinica.api.dto.financeiro.TransacaoResponseDTO;
import com.clinica.api.service.TransacaoFinanceiraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoFinanceiraController {

    private final TransacaoFinanceiraService service;

    public TransacaoFinanceiraController(TransacaoFinanceiraService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> criar(@RequestBody @Valid TransacaoRequestDTO dto) {
        TransacaoResponseDTO novaTransacao = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTransacao);
    }

    @GetMapping
    public ResponseEntity<List<TransacaoResponseDTO>> listarTodas() {
        List<TransacaoResponseDTO> transacoes = service.listarTodas();
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoResponseDTO> buscarPorId(@PathVariable Long id) {
        TransacaoResponseDTO transacao = service.buscarPorId(id);
        return ResponseEntity.ok(transacao);
    }

    @GetMapping("/resumo")
    public ResponseEntity<ResumoFinanceiroDTO> obterResumoFinanceiro() {
        ResumoFinanceiroDTO resumo = service.obterResumoFinanceiro();
        return ResponseEntity.ok(resumo);
    }
}
