package com.clinica.api.controller;

import java.util.List;

import com.clinica.api.dto.Agendamento.AgendamentoRequestDTO;
import com.clinica.api.dto.Agendamento.AgendamentoResponseDTO;
import com.clinica.api.dto.estoque.RegistrarUsoInsumoDTO;
import com.clinica.api.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    /**
     * Endpoint POST para criar um novo agendamento, validando os dados de entrada.
     */
    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criar(@RequestBody @Valid AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO agendamentoCriado = agendamentoService.criarComDto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoCriado);
    }

    /**
     * Endpoint GET para listar todos os agendamentos registados.
     */
    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listar() {
        List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }

    /**
     * Endpoint PATCH para concluir um agendamento específico, permitindo opcionalmente
     * o envio de uma lista dinâmica de insumos utilizados para a baixa automática no stock.
     */
    @PatchMapping("/{id}/concluir")
    public ResponseEntity<AgendamentoResponseDTO> concluirAgendamento(
            @PathVariable Long id,
            @RequestBody(required = false) List<RegistrarUsoInsumoDTO> insumosUsados) {

        AgendamentoResponseDTO resposta = agendamentoService.concluirAgendamento(id, insumosUsados);
        return ResponseEntity.ok(resposta);
    }
}