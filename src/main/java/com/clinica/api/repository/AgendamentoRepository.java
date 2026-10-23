package com.clinica.api.repository;

import com.clinica.api.domain.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    /**
     * Busca todos os agendamentos carregando antecipadamente os relacionamentos
     * (cliente, serviço e funcionário) para evitar o problema de N+1 consultas.
     */
    @Query("SELECT a FROM Agendamento a " +
            "LEFT JOIN FETCH a.cliente " +
            "LEFT JOIN FETCH a.servico " +
            "LEFT JOIN FETCH a.funcionario")
    List<Agendamento> findAllComRelacionamentos();

    /**
     * Busca um agendamento específico por ID carregando todos os seus
     * relacionamentos de forma otimizada.
     */
    @Query("SELECT a FROM Agendamento a " +
            "LEFT JOIN FETCH a.cliente " +
            "LEFT JOIN FETCH a.servico " +
            "LEFT JOIN FETCH a.funcionario " +
            "WHERE a.id = :id")
    Optional<Agendamento> findByIdComRelacionamentos(@Param("id") Long id);

    /**
     * Verifica se já existe algum agendamento para o mesmo funcionário
     * em conflito com o intervalo de horários informado.
     */
    @Query("SELECT COUNT(a) > 0 FROM Agendamento a " +
            "WHERE a.funcionario.id = :funcionarioId " +
            "AND a.dataHoraInicio < :fim " +
            "AND a.dataHoraFim > :inicio")
    boolean existeConflitoHorario(@Param("funcionarioId") Long funcionarioId,
                                  @Param("inicio") LocalDateTime inicio,
                                  @Param("fim") LocalDateTime fim);
}