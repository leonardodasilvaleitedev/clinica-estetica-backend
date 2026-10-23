package com.clinica.api.controller;

import com.clinica.api.dto.cliente.ClienteRequest;
import com.clinica.api.dto.cliente.ClienteResponseDTO;
import com.clinica.api.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;


    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Endpoint POST para cadastrar um novo cliente, retornando o status 201 Created
     * com o cabeçalho Location apontando para o recurso recém-criado.
     */
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrar(
            @RequestBody @Valid ClienteRequest dados,
            UriComponentsBuilder uriBuilder) {

        var dto = clienteService.cadastrar(dados);
        var uri = uriBuilder.path("/api/clientes/{id}").buildAndExpand(dto.id()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    /**
     * Endpoint GET para listar os clientes de forma paginada e ordenada alfabeticamente por nome.
     */
    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> listar(
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

        var pagina = clienteService.listar(paginacao);
        return ResponseEntity.ok(pagina);
    }

    /**
     * Endpoint GET para consultar os detalhes de um cliente específico através do seu ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        var cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }
}