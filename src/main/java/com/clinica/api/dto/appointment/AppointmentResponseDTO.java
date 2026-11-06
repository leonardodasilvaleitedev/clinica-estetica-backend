package com.clinica.api.dto.appointment;

import com.clinica.api.domain.entity.Appointment;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AppointmentResponseDTO(
        Long id,
        Long clientId,
        String clientName,
        Long serviceId,
        String serviceName,
        Long employeeId,
        String employeeName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        BigDecimal priceCharged,
        String status,
        String notes
) {

    /**
     * Construtor que mapeia os dados da entidade Appointment para o DTO de resposta,
     * garantindo proteção contra valores nulos (null-safety) nos relacionamentos.
     */
    public AppointmentResponseDTO(Appointment appointment) {
        this(
                appointment.getId(),
                appointment.getClient() != null ? appointment.getClient().getId() : null,
                appointment.getClient() != null ? appointment.getClient().getName() : null,
                appointment.getService() != null ? appointment.getService().getId() : null,
                appointment.getService() != null ? appointment.getService().getName() : null,
                appointment.getEmployee() != null ? appointment.getEmployee().getId() : null,
                appointment.getEmployee() != null ? appointment.getEmployee().getName() : null,
                appointment.getStartDateTime(),
                appointment.getEndDateTime(),
                appointment.getPriceCharged(),
                appointment.getStatus() != null ? appointment.getStatus().name() : null,
                appointment.getNotes()
        );
    }
}