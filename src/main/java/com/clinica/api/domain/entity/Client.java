package com.clinica.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // CPF único e de preenchimento obrigatório para identificação do client
    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(name = "email")
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String gender;

    // Campo dedicado para anotações clínicas e restrições de saúde do client
    @Column(name = "medical_notes")
    private String medicalNotes;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}