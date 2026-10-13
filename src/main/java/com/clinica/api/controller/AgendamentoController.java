package com.clinica.api.controller;

import com.clinica.api.domain.enums.StatusAgendamento;
import com.clinica.api.dto.Agendamento.AgendamentoRequestDTO;
import com.clinica.api.dto.Agendamento.AgendamentoResponseDTO;
import com.clinica.api.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    /**
     * Cria um novo agendamento.
     * POST /api/agendamentos
     */
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody AgendamentoRequestDTO dto) {
        var agendamento = agendamentoService.criarComDto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamento);
    }

    /**
     * Lista todos os agendamentos.
     * GET /api/agendamentos
     */
    @GetMapping
    public ResponseEntity<?> listar() {
        var agendamentos = agendamentoService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }

    /**
     * Busca um agendamento pelo ID.
     * GET /api/agendamentos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        var agendamento = agendamentoService.buscarPorIdDto(id);
        return ResponseEntity.ok(agendamento);
    }

    /**
     * Atualiza o status de um agendamento.
     * PATCH /api/agendamentos/{id}/status?status=CONFIRMADO
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusAgendamento status) {
        var agendamento = agendamentoService.atualizarStatus(id, status);
        return ResponseEntity.ok(agendamento);
    }
}