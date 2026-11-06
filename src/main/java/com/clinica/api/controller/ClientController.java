package com.clinica.api.controller;

import com.clinica.api.dto.client.ClientRequestDTO;
import com.clinica.api.dto.client.ClientResponseDTO;
import com.clinica.api.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Endpoint POST para cadastrar um novo client, retornando o status 201 Created
     * com o cabeçalho Location apontando para o recurso recém-criado.
     */
    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(
            @RequestBody @Valid ClientRequestDTO dto,
            UriComponentsBuilder uriBuilder) {

        var responseDto = clientService.create(dto);
        var uri = uriBuilder.path("/api/clientes/{id}").buildAndExpand(responseDto.id()).toUri();

        return ResponseEntity.created(uri).body(responseDto);
    }

    /**
     * Endpoint GET para listar os clientes de forma paginada e ordenada alfabeticamente por nome.
     */
    @GetMapping
    public ResponseEntity<Page<ClientResponseDTO>> findAll(
            @PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {

        var page = clientService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * Endpoint GET para consultar os detalhes de um client específico através do seu ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
        var client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }
}