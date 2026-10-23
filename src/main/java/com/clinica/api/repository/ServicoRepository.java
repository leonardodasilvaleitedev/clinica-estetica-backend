package com.clinica.api.repository;

import com.clinica.api.domain.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Herda automaticamente os métodos CRUD padrão para a entidade Servico
@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

}