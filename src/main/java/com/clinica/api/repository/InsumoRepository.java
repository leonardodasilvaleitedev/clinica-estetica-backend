package com.clinica.api.repository;

import com.clinica.api.domain.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsumoRepository extends JpaRepository<Insumo, Long> {

}
