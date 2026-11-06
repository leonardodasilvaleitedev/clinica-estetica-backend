package com.clinica.api.repository;

import com.clinica.api.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

     /**
     * Busca um funcionário cadastrado pelo seu e-mail,
     * retornando um Optional para tratamento seguro de nulos.
     */
    Optional<Employee> findByEmail(String email);
}