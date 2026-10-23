package com.clinica.api.repository;

import com.clinica.api.domain.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Herda automaticamente métodos CRUD padrão e paginação para a entidade Insumo
@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {

}
