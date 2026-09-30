package com.clinica.api.controller;

import com.clinica.api.dto.servicos.ServicoRequestDTO;
import com.clinica.api.dto.servicos.ServicoResponseDTO;
import com.clinica.api.domain.model.Servico;
import com.clinica.api.repository.ServicoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoRepository servicoRepository;

    public ServicoController(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    @PostMapping
    public ResponseEntity<ServicoResponseDTO> criar(@RequestBody @Valid ServicoRequestDTO dto) {
        var servico = Servico.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .duracaoMinutos(dto.duracaoMinutos())
                .precoBase(dto.preco())
                .ativo(dto.ativo() != null ? dto.ativo() : true)
                .build();

        Servico salvo = servicoRepository.save(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ServicoResponseDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> listar() {
        var servicos = servicoRepository.findAll()
                .stream()
                .map(ServicoResponseDTO::new)
                .toList();
        return ResponseEntity.ok(servicos);
    }
}