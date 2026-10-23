package com.clinica.api.repository;

import com.clinica.api.domain.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

     /**
     * Busca um funcionário cadastrado pelo seu e-mail,
     * retornando um Optional para tratamento seguro de nulos.
     */
    Optional<Funcionario> findByEmail(String email);
}