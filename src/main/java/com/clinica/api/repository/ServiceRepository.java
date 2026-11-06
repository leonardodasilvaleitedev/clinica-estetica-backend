package com.clinica.api.repository;

import com.clinica.api.domain.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Herda automaticamente os métodos CRUD padrão para a entidade Service
@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

}