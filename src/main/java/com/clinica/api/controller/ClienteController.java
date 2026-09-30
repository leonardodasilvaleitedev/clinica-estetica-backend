package com.clinica.api.controller;

import com.clinica.api.dto.cliente.ClienteRequest;
import com.clinica.api.dto.cliente.ClienteResponse;
import com.clinica.api.domain.model.Cliente;
import com.clinica.api.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<ClienteResponse> cadastrar(
            @RequestBody @Valid ClienteRequest dados,
            UriComponentsBuilder uriBuilder) {

        var cliente = Cliente.builder()
                .nome(dados.nome())
                .cpf(dados.cpf())
                .telefone(dados.telefone())
                .email(dados.email())
                .sexo(dados.sexo())
                .dataNascimento(dados.dataNascimento())
                .build();

            clienteRepository.save(cliente);

        var uri = uriBuilder.path("/clientes/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClienteResponse(cliente));
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponse>> listar(
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

        var pagina = clienteRepository.findAll(paginacao)
                .map(ClienteResponse::new);

        return ResponseEntity.ok(pagina);
    }
}