package com.clinica.api.service;

import com.clinica.api.domain.entity.Client;
import com.clinica.api.dto.client.ClientRequestDTO;
import com.clinica.api.dto.client.ClientResponseDTO;
import com.clinica.api.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    // Injeção de dependência do repositório de clientes via construtor
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Cadastra um novo client após validar se o CPF informado já está registrado,
     * utilizando o padrão Builder para instanciar a entidade e retornando o DTO de resposta.
     */
    @Transactional
    public ClientResponseDTO create(ClientRequestDTO data) {
        if (clientRepository.existsByCpf(data.cpf())) {
            throw new IllegalArgumentException("Já existe um client cadastrado com este CPF.");
        }

        var cliente = Client.builder()
                .name(data.name())
                .cpf(data.cpf())
                .phone(data.phone())
                .email(data.email())
                .gender(data.gender())
                .birthDate(data.birthDate())
                .medicalNotes(data.medicalNotes())
                .active(true)
                .build();

        var salvo = clientRepository.save(cliente);
        return new ClientResponseDTO(salvo);
    }

    /**
     * Lista os clientes de forma paginada, convertendo cada entidade
     * diretamente para o seu respectivo DTO de resposta.
     */
    public Page<ClientResponseDTO> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable)
                .map(ClientResponseDTO::new);
    }

    /**
     * Busca um client específico pelo ID, lançando uma exceção
     * caso o registro não seja encontrado no banco de dados.
     */
    public ClientResponseDTO findById(Long id) {
        var client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client não encontrado."));
        return new ClientResponseDTO(client);
    }

}
