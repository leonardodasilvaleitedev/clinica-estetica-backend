package com.clinica.api.domain.entity;

import com.clinica.api.domain.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento N para 1 com Client (Carregamento otimizado com LAZY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Relacionamento N para 1 com o Serviço prestado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    // Relacionamento N para 1 com o Funcionário responsável pelo atendimento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_time")
    private LocalDateTime endDateTime;

    // Valor monetário cobrado (com precisão exata para valores financeiros)
    @Column(name = "price_charged", precision = 10, scale = 2)
    private BigDecimal priceCharged;


    // Armazena o enum do status como texto na base de dados
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Column(name = "notes")
    private String notes;

    // Regista automaticamente a data e hora de criação do registo
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

}