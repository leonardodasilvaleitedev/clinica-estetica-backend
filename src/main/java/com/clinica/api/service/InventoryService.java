package com.clinica.api.service;

import com.clinica.api.domain.entity.Employee;
import com.clinica.api.domain.entity.Supply;
import com.clinica.api.domain.entity.SupplyUsage;
import com.clinica.api.dto.inventory.SupplyDTO;
import com.clinica.api.dto.inventory.RegisterSupplyUsageDTO;
import com.clinica.api.dto.inventory.SupplyUsageResponseDTO;
import com.clinica.api.repository.EmployeeRepository;
import com.clinica.api.repository.SupplyRepository;
import com.clinica.api.repository.SupplyUsageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final SupplyRepository supplyRepository;
    private final SupplyUsageRepository supplyUsageRepository;
    private final EmployeeRepository employeeRepository;

    // Injeção de dependências dos repositórios via construtor
    public InventoryService(SupplyRepository supplyRepository, SupplyUsageRepository supplyUsageRepository, EmployeeRepository employeeRepository) {
        this.supplyRepository = supplyRepository;
        this.supplyUsageRepository = supplyUsageRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Cadastra um novo insumo no inventory a partir do DTO recebido,
     * salvando no banco de dados e retornando o DTO correspondente.
     */
    @Transactional
    public SupplyDTO registerSupply(SupplyDTO dto){
        Supply supply = Supply.builder()
                .name(dto.name())
                .brand(dto.brand())
                .unitOfMeasure(dto.unitOfMeasure())
                .stockQuantity(dto.stockQuantity())
                .minimumStock(dto.minimumStock())
                .build();

        Supply salvo = supplyRepository.save(supply);
        return new SupplyDTO(
                salvo.getId(),
                salvo.getName(),
                salvo.getBrand(),
                salvo.getUnitOfMeasure(),
                salvo.getStockQuantity(),
                salvo.getMinimumStock()
        );
    }

    /**
     * Lista todos os insumos cadastrados no inventory,
     * convertendo cada entidade em seu respectivo DTO.
     */
    public List<SupplyDTO> findAllSupplies(){
        return supplyRepository.findAll().stream().map(i -> new SupplyDTO(
                i.getId(),
                i.getName(),
                i.getBrand(),
                i.getUnitOfMeasure(),
                i.getStockQuantity(),
                i.getMinimumStock()
        )).toList();
    }

    /**
     * Registra o uso de um insumo por um funcionário, abatendo a quantidade
     * do inventory e disparando exceção caso o saldo seja insuficiente.
     */
    @Transactional
    public SupplyUsageResponseDTO registerUsage(RegisterSupplyUsageDTO dto){
        Employee employee = employeeRepository.findById(dto.employeeId())
                .orElseThrow(()-> new IllegalArgumentException("Employee não encontrado com ID:" + dto.employeeId()));
        Supply supply = supplyRepository.findById(dto.supplyId())
                .orElseThrow(()-> new IllegalArgumentException("Supply não encontrado com ID:" + dto.supplyId()));

        // Abate do inventory (dispara exceção se a quantidade for insuficiente)
        supply.deductStock(dto.quantityUsed());
        supplyRepository.save(supply);

        // Registro do consumo
        SupplyUsage usage = new SupplyUsage();
        usage.setEmployee(employee);
        usage.setSupply(supply);
        usage.setQuantityUsed(dto.quantityUsed());
        usage.setNotes(dto.notes());

        SupplyUsage savedUsage = supplyUsageRepository.save(usage);

        return new SupplyUsageResponseDTO(
                savedUsage.getId(),
                savedUsage.getEmployee().getName(),
                savedUsage.getSupply().getName(),
                savedUsage.getQuantityUsed(),
                savedUsage.getUsedAt(),
                savedUsage.getNotes()
        );
    }

    /**
     * Lista todos os registros de uso de insumos vinculados a um funcionário específico.
     */
    public List<SupplyUsageResponseDTO> findUsageByEmployeeId(Long employeeId){
        return supplyUsageRepository.findByEmployeeId(employeeId).stream()
                .map(u-> new SupplyUsageResponseDTO(
                        u.getId(),
                        u.getEmployee().getName(),
                        u.getSupply().getName(),
                        u.getQuantityUsed(),
                        u.getUsedAt(),
                        u.getNotes()
                )).toList();
    }

}