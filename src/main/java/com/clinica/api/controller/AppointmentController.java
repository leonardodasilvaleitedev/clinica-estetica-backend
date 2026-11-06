package com.clinica.api.controller;

import java.util.List;

import com.clinica.api.dto.appointment.AppointmentRequestDTO;
import com.clinica.api.dto.appointment.AppointmentResponseDTO;
import com.clinica.api.dto.inventory.RegisterSupplyUsageDTO;
import com.clinica.api.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Endpoint POST para criar um novo agendamento, validando os dados de entrada.
     */
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> create(@RequestBody @Valid AppointmentRequestDTO dto) {
        AppointmentResponseDTO createAppointment = appointmentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAppointment);
    }

    /**
     * Endpoint GET para listar todos os agendamentos registados.
     */
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> findAll() {
        List<AppointmentResponseDTO> agendamentos = appointmentService.findAll();
        return ResponseEntity.ok(agendamentos);
    }

    /**
     * Endpoint PATCH para concluir um agendamento específico, permitindo opcionalmente
     * o envio de uma lista dinâmica de insumos utilizados para a baixa automática no stock.
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<AppointmentResponseDTO> completeAppointment(
            @PathVariable Long id,
            @RequestBody(required = false) List<RegisterSupplyUsageDTO> usedSupplies) {

        AppointmentResponseDTO response = appointmentService.completeAppointment(id, usedSupplies);
        return ResponseEntity.ok(response);
    }
}