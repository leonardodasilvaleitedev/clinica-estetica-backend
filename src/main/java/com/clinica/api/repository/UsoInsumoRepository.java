package com.clinica.api.repository;

import com.clinica.api.domain.model.UsoInsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsoInsumoRepository extends JpaRepository<UsoInsumo, Long> {
    List<UsoInsumo> findByFuncionarioId(Long funcionarioId);
    List<UsoInsumo> findByInsumoId(Long insumoId);
}


