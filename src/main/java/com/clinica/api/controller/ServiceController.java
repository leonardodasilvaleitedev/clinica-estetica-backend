package com.clinica.api.controller;

import com.clinica.api.dto.service.ServiceRequestDTO;
import com.clinica.api.dto.service.ServiceResponseDTO;
import com.clinica.api.domain.entity.Service;
import com.clinica.api.repository.ServiceRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    private final ServiceRepository serviceRepository;

    public ServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Endpoint POST para cadastrar um novo serviço/procedimento estético na base de dados.
     */
    @PostMapping
    public ResponseEntity<ServiceResponseDTO> create(@RequestBody @Valid ServiceRequestDTO dto) {
        var service = Service.builder()
                .name(dto.name())
                .description(dto.description())
                .durationMinutes(dto.durationMinutes())
                .price(dto.price())
                .active(dto.active() != null ? dto.active() : true)
                .build();

        Service savedService = serviceRepository.save(service);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ServiceResponseDTO(savedService));
    }

    /**
     * Endpoint GET para listar todos os serviços disponíveis na clínica.
     */
    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> findAll() {
        var services = serviceRepository.findAll()
                .stream()
                .map(ServiceResponseDTO::new)
                .toList();
        return ResponseEntity.ok(services);
    }
}