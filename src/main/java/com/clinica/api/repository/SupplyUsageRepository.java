package com.clinica.api.repository;

import com.clinica.api.domain.entity.SupplyUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SupplyUsageRepository extends JpaRepository<SupplyUsage, Long> {

    /**
     * Busca uma lista de registros de uso de insumos associados a um funcionário específico,
     * utilizando o ID do funcionário como critério de filtro.
     */
    List<SupplyUsage> findByEmployeeId(Long employeeId);

    /**
     * Busca uma lista de registros de uso vinculados a um insumo específico,
     * utilizando o ID do insumo como critério de filtro.
     */
    List<SupplyUsage> findBySupplyId(Long supplyId);
}


