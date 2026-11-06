package com.clinica.api.service;

import com.clinica.api.domain.entity.Appointment;
import com.clinica.api.domain.entity.Client;
import com.clinica.api.domain.entity.Employee;
import com.clinica.api.domain.entity.Service;
import com.clinica.api.domain.enums.AppointmentStatus;
import com.clinica.api.domain.enums.TransactionType;
import com.clinica.api.dto.appointment.AppointmentRequestDTO;
import com.clinica.api.dto.appointment.AppointmentResponseDTO;
import com.clinica.api.dto.inventory.RegisterSupplyUsageDTO;
import com.clinica.api.dto.finance.TransactionRequestDTO;
import com.clinica.api.repository.AppointmentRepository;
import com.clinica.api.repository.ClientRepository;
import com.clinica.api.repository.EmployeeRepository;
import com.clinica.api.repository.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final EmployeeRepository employeeRepository;
    private final FinancialTransactionService financialTransactionService;
    private final InventoryService inventoryService;

    // Injeção de dependências via construtor
    public AppointmentService(AppointmentRepository appointmentRepository,
                              ClientRepository clientRepository,
                              ServiceRepository serviceRepository,
                              EmployeeRepository employeeRepository,
                              FinancialTransactionService financialTransactionService,
                              InventoryService inventoryService) {
        this.appointmentRepository = appointmentRepository;
        this.clientRepository = clientRepository;
        this.serviceRepository = serviceRepository;
        this.employeeRepository = employeeRepository;
        this.financialTransactionService = financialTransactionService;
        this.inventoryService = inventoryService;
    }

    /**
     * Cria um novo agendamento validando a existência do client, serviço e funcionário,
     * calculando valores e definindo a data/hora de término com base na duração do serviço.
     */
    @Transactional
    public AppointmentResponseDTO create(AppointmentRequestDTO dto) {
        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new EntityNotFoundException("Client não encontrado com ID: " + dto.clientId()));

        Service service = serviceRepository.findById(dto.serviceId())
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado com ID: " + dto.serviceId()));

        Employee employee = employeeRepository.findById(dto.employeeId())
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com ID: " + dto.employeeId()));

        Appointment appointment = new Appointment();
        appointment.setClient(client);
        appointment.setService(service);
        appointment.setEmployee(employee);
        appointment.setStartDateTime(dto.startDateTime());
        appointment.setNotes(dto.notes());
        appointment.setCreatedAt(LocalDateTime.now());

        // Define o status padrão como AGENDADO caso não venha preenchido
        if (appointment.getStatus() == null) {
            appointment.setStatus(AppointmentStatus.SCHEDULED);
        }

        // Define o valor cobrado (se não informado, assume o preço base do serviço)
        if (dto.priceCharged() != null) {
            appointment.setPriceCharged(dto.priceCharged());
        } else {
            appointment.setPriceCharged(service.getPrice());
        }

        // Calcula a data/hora de término com base na duração em minutos do serviço
        if (service.getDurationMinutes() != null) {
            appointment.setEndDateTime(dto.startDateTime().plusMinutes(service.getDurationMinutes()));
        } else {
            appointment.setEndDateTime(dto.startDateTime().plusHours(1));
        }

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return new AppointmentResponseDTO(savedAppointment);
    }

    // MÉTODO PARA LISTAR TODOS OS AGENDAMENTOS
    public List<AppointmentResponseDTO> findAll() {
        return appointmentRepository.findAllWithRelationships().stream()
                .map(AppointmentResponseDTO::new)
                .toList();
    }


    /**
     * Conclui um agendamento, gera automaticamente a transação financeira de receita
     * e processa a baixa dinâmica de insumos utilizados no inventory.
     */
    @Transactional
    public AppointmentResponseDTO completeAppointment(Long id, List<RegisterSupplyUsageDTO> usedSupplies) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment não encontrado com ID: " + id));

        appointment.setStatus(AppointmentStatus.COMPLETED);
        Appointment updateAppointment = appointmentRepository.save(appointment);

        // Gerar transação financeira automaticamente
        TransactionRequestDTO transactionDto = TransactionRequestDTO.builder()
                .description("Atendimento: " + appointment.getService().getName() + " - Client: " + appointment.getClient().getName())
                .amount(appointment.getPriceCharged())
                .type(TransactionType.INCOME)
                .paymentMethod("PIX")
                .transactionDate(LocalDate.now())
                .paymentDate(LocalDateTime.now())
                .appointmentId(appointment.getId())
                .build();

        financialTransactionService.create(transactionDto);

        // Processar a lista dinâmica de insumos enviados pelo front-end
        if (usedSupplies != null && !usedSupplies.isEmpty()) {
            Long employeeId = appointment.getEmployee().getId();

            for (RegisterSupplyUsageDTO item : usedSupplies) {
                RegisterSupplyUsageDTO usageDto = new RegisterSupplyUsageDTO(
                        employeeId,
                        item.supplyId(),
                        item.quantityUsed(),
                        item.notes() != null ? item.notes() : "Baixa automática via conclusão do appointment #" + appointment.getId()
                );

                inventoryService.registerUsage(usageDto);
            }
        }

        return new AppointmentResponseDTO(updateAppointment);
    }
}