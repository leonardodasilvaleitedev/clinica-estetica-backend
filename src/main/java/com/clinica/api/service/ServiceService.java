package com.clinica.api.service;

import com.clinica.api.domain.entity.Service;
import com.clinica.api.dto.service.ServiceRequestDTO;
import com.clinica.api.dto.service.ServiceResponseDTO;
import com.clinica.api.repository.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository repository;

    // Injeção de dependência do repositório de serviços via construtor
    public ServiceService(ServiceRepository repository) {
        this.repository = repository;
    }

    /**
     * Cadastra um novo serviço utilizando o padrão Builder para instanciar a entidade
     * com base no DTO de requisição, salvando no banco e retornando o DTO de resposta.
     */
    @Transactional
    public ServiceResponseDTO create(ServiceRequestDTO dto) {
        Service service = Service.builder()
                .name(dto.name())
                .description(dto.description())
                .durationMinutes(dto.durationMinutes())
                .price(dto.price())
                .active(dto.active())
                .build();

        Service saved = repository.save(service);
        return new ServiceResponseDTO(saved);
    }

    /**
     * Lista todos os serviços cadastrados, convertendo cada entidade
     * em seu respectivo DTO de resposta através de Streams.
     */
    public List<ServiceResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(ServiceResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um serviço específico pelo ID, lançando uma exceção
     * caso o registro não seja encontrado no banco de dados.
     */
    public ServiceResponseDTO findById(Long id) {
        Service service = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado com o ID: " + id));
        return new ServiceResponseDTO(service);
    }

    /**
     * Atualiza os dados de um serviço existente após localizá-lo por ID,
     * persistindo as alterações e retornando o DTO atualizado.
     */
    @Transactional
    public ServiceResponseDTO update(Long id, ServiceRequestDTO dto) {
        Service service = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado com o ID: " + id));

        service.setName(dto.name());
        service.setDescription(dto.description());
        service.setDurationMinutes(dto.durationMinutes());
        service.setPrice(dto.price());
        service.setActive(dto.active());

        Service updated = repository.save(service);
        return new ServiceResponseDTO(updated);
    }

    /**
     * Exclui um serviço do sistema após verificar se o ID existe,
     * lançando uma exceção caso contrário.
     */
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Serviço não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}