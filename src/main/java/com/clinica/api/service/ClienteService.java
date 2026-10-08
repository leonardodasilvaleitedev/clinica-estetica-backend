package com.clinica.api.service;

import com.clinica.api.domain.model.Cliente;
import com.clinica.api.dto.cliente.ClienteRequest;
import com.clinica.api.dto.cliente.ClienteResponseDTO;
import com.clinica.api.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteResponseDTO cadastrar(ClienteRequest dados) {
        if (clienteRepository.existsByCpf(dados.cpf())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF.");
        }

        var cliente = Cliente.builder()
                .nome(dados.nome())
                .cpf(dados.cpf())
                .telefone(dados.telefone())
                .email(dados.email())
                .sexo(dados.sexo())
                .dataNascimento(dados.dataNascimento())
                .observacoesMedicas(dados.observacoesMedicas())
                .ativo(true)
                .build();

        var salvo = clienteRepository.save(cliente);
        return new ClienteResponseDTO(salvo);
    }

    public Page<ClienteResponseDTO> listar(Pageable paginacao) {
        return clienteRepository.findAll(paginacao)
                .map(ClienteResponseDTO::new);
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
        return new ClienteResponseDTO(cliente);
    }
}
