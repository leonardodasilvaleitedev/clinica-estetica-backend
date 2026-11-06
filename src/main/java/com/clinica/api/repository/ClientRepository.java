package com.clinica.api.repository;

import com.clinica.api.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

     /**
     * Busca um client cadastrado utilizando o CPF como critério de filtro,
     * retornando um Optional para tratamento seguro de valores ausentes.
     */
    Optional<Client> findByCpf(String cpf);

     /**
     * Verifica rapidamente se já existe algum client cadastrado com o CPF informado,
     * retornando true ou false.
     */
    boolean existsByCpf(String cpf);
}