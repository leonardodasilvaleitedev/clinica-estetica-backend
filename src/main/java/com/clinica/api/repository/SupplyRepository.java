package com.clinica.api.repository;

import com.clinica.api.domain.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Herda automaticamente métodos CRUD padrão e paginação para a entidade Supply
@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {

}
