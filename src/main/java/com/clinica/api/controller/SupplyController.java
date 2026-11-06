package com.clinica.api.controller;

import com.clinica.api.dto.inventory.SupplyDTO;
import com.clinica.api.dto.inventory.RegisterSupplyUsageDTO;
import com.clinica.api.dto.inventory.SupplyUsageResponseDTO;
import com.clinica.api.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplies")
public class SupplyController {
    private final InventoryService inventoryService;

    public SupplyController(InventoryService inventoryService) {
     this.inventoryService = inventoryService;
    }

    /**
     * Endpoint POST para cadastrar um novo insumo no sistema.
     */
    @PostMapping
    public ResponseEntity<SupplyDTO> create(@RequestBody SupplyDTO dto){
        SupplyDTO createdSupply = inventoryService.registerSupply(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupply);
    }

    /**
     * Endpoint GET para listar todos os insumos disponíveis no stock.
     */
    @GetMapping
    public ResponseEntity<List<SupplyDTO>> findAll(){
        return ResponseEntity.ok(inventoryService.findAllSupplies());
    }

    /**
     * Endpoint POST para registar manualmente a utilização de um insumo por um funcionário.
     */
    @PostMapping("/usages")
    public ResponseEntity<SupplyUsageResponseDTO> registerUsage(@RequestBody RegisterSupplyUsageDTO dto){
        SupplyUsageResponseDTO registerUsage = inventoryService.registerUsage(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUsage);
    }

    /**
     * Endpoint GET para listar o histórico de uso de insumos associado a um funcionário específico.
     */
    @GetMapping("/usages/employee/{employeeId}")
    public ResponseEntity<List<SupplyUsageResponseDTO>> findByEmployeeId(@PathVariable Long employeeId){
        return ResponseEntity.ok(inventoryService.findUsageByEmployeeId(employeeId));
    }
}