package com.clinica.api.controller;

import com.clinica.api.dto.estoque.InsumoDTO;
import com.clinica.api.dto.estoque.RegistrarUsoInsumoDTO;
import com.clinica.api.dto.estoque.UsoInsumoResponseDTO;
import com.clinica.api.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos")
public class InsumoController {
    private final EstoqueService estoqueService;

    public InsumoController(EstoqueService estoqueService) {
     this.estoqueService = estoqueService;
    }

    /**
     * Endpoint POST para cadastrar um novo insumo no sistema.
     */
    @PostMapping
    public ResponseEntity<InsumoDTO> cadastrarInsumo(@RequestBody InsumoDTO dto){
        InsumoDTO criado = estoqueService.cadastroInsumo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    /**
     * Endpoint GET para listar todos os insumos disponíveis no stock.
     */
    @GetMapping
    public ResponseEntity<List<InsumoDTO>> listarInsumos(){
        return ResponseEntity.ok(estoqueService.listaInsumos());
    }

    /**
     * Endpoint POST para registar manualmente a utilização de um insumo por um funcionário.
     */
    @PostMapping("/usos")
    public ResponseEntity<UsoInsumoResponseDTO> registrarUso(@RequestBody RegistrarUsoInsumoDTO dto){
        UsoInsumoResponseDTO registrado = estoqueService.registrarUso(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrado);
    }

    /**
     * Endpoint GET para listar o histórico de uso de insumos associado a um funcionário específico.
     */
    @GetMapping("/usos/funcionario/{funcionarioId}")
    public ResponseEntity<List<UsoInsumoResponseDTO>> listarPorFuncionario(@PathVariable Long funcionarioId){
        return ResponseEntity.ok(estoqueService.listarUsoPorFuncionario(funcionarioId));
    }
}