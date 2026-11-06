package com.clinica.api.repository;

import com.clinica.api.domain.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Busca todos os agendamentos carregando antecipadamente os relacionamentos
     * (client, serviço e funcionário) para evitar o problema de N+1 consultas.
     */
    @Query("SELECT a FROM Appointment a " +
            "LEFT JOIN FETCH a.client " +
            "LEFT JOIN FETCH a.service " +
            "LEFT JOIN FETCH a.employee")
    List<Appointment> findAllWithRelationships();

    /**
     * Busca um agendamento específico por ID carregando todos os seus
     * relacionamentos de forma otimizada.
     */
    @Query("SELECT a FROM Appointment a " +
            "LEFT JOIN FETCH a.client " +
            "LEFT JOIN FETCH a.service " +
            "LEFT JOIN FETCH a.employee " +
            "WHERE a.id = :id")
    Optional<Appointment> findByIdWithRelationships(@Param("id") Long id);

    /**
     * Verifica se já existe algum agendamento para o mesmo funcionário
     * em conflito com o intervalo de horários informado.
     */
    @Query("SELECT COUNT(a) > 0 FROM Appointment a " +
            "WHERE a.employee.id = :employeeId " +
            "AND a.startDateTime < :andDateTime " +
            "AND a.endDateTime > :startDateTime")
    boolean hasSchedulingConflict(@Param("employeeId") Long employeeId,
                                  @Param("startDateTime") LocalDateTime startDateTime,
                                  @Param("endDateTime") LocalDateTime endDateTime);
}