package com.clinica.api.repository;

import com.clinica.api.domain.model.UsoInsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsoInsumoRepository extends JpaRepository<UsoInsumo, Long> {

    /**
     * Busca uma lista de registros de uso de insumos associados a um funcionário específico,
     * utilizando o ID do funcionário como critério de filtro.
     */
    List<UsoInsumo> findByFuncionarioId(Long funcionarioId);

    /**
     * Busca uma lista de registros de uso vinculados a um insumo específico,
     * utilizando o ID do insumo como critério de filtro.
     */
    List<UsoInsumo> findByInsumoId(Long insumoId);
}


