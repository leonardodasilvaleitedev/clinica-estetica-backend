package com.clinica.api.controller;

import com.clinica.api.domain.enums.Role;
import com.clinica.api.domain.entity.Employee;
import com.clinica.api.dto.employee.EmployeeRequestDTO;
import com.clinica.api.dto.employee.EmployeeResponseDTO;
import com.clinica.api.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeController(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Endpoint POST para cadastrar um novo funcionário, aplicando o hash da senha
     * com BCrypt e guardando os dados na base de dados.
     */
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> create(@RequestBody @Valid EmployeeRequestDTO dto) {
        var employee = Employee.builder()
                .name(dto.name())
                .cpf(dto.cpf())
                .email(dto.email())
                .phone(dto.phone())
                .passwordHash(passwordEncoder.encode(dto.password()))
                .role(Role.valueOf(dto.role().toUpperCase()))
                .commissionPercentage(dto.commissionPercentage())
                .active(dto.active() != null ? dto.active() : true)
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EmployeeResponseDTO(savedEmployee));
    }

    /**
     * Endpoint GET para listar todos os funcionários registados no sistema.
     */
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> findAll() {
        var employees = employeeRepository.findAll()
                .stream()
                .map(EmployeeResponseDTO::new)
                .toList();
        return ResponseEntity.ok(employees);
    }
}
