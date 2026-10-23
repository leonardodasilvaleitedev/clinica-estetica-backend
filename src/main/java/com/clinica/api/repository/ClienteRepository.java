package com.clinica.api.repository;

import com.clinica.api.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

     /**
     * Busca um cliente cadastrado utilizando o CPF como critério de filtro,
     * retornando um Optional para tratamento seguro de valores ausentes.
     */
    Optional<Cliente> findByCpf(String cpf);

     /**
     * Verifica rapidamente se já existe algum cliente cadastrado com o CPF informado,
     * retornando true ou false.
     */
    boolean existsByCpf(String cpf);
}