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

    @Query("SELECT a FROM Agendamento a " +
            "LEFT JOIN FETCH a.cliente " +
            "LEFT JOIN FETCH a.servico " +
            "LEFT JOIN FETCH a.funcionario")
    List<Agendamento> findAllComRelacionamentos();

    @Query("SELECT a FROM Agendamento a " +
            "LEFT JOIN FETCH a.cliente " +
            "LEFT JOIN FETCH a.servico " +
            "LEFT JOIN FETCH a.funcionario " +
            "WHERE a.id = :id")
    Optional<Agendamento> findByIdComRelacionamentos(@Param("id") Long id);

    @Query("SELECT COUNT(a) > 0 FROM Agendamento a " +
            "WHERE a.funcionario.id = :funcionarioId " +
            "AND a.dataHoraInicio < :fim " +
            "AND a.dataHoraFim > :inicio")
    boolean existeConflitoHorario(@Param("funcionarioId") Long funcionarioId,
                                  @Param("inicio") LocalDateTime inicio,
                                  @Param("fim") LocalDateTime fim);
}