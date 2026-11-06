package com.clinica.api.service;

import com.clinica.api.domain.enums.Role;
import com.clinica.api.domain.entity.Employee;
import com.clinica.api.dto.employee.EmployeeRequestDTO;
import com.clinica.api.dto.employee.EmployeeResponseDTO;
import com.clinica.api.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    // Injeção de dependência do repositório de funcionários via construtor
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    /**
     * Cadastra um novo funcionário utilizando o padrão Builder para instanciar a entidade,
     * convertendo a String do role para o Enum correspondente e retornando o DTO de resposta.
     */
    @Transactional
    public EmployeeResponseDTO criar(EmployeeRequestDTO dto) {
        Employee employee = Employee.builder()
                .name(dto.name())
                .cpf(dto.cpf())
                .email(dto.email())
                .phone(dto.phone())
                .passwordHash(dto.password())
                .role(Role.valueOf(dto.role()))
                .commissionPercentage(dto.commissionPercentage())
                .active(dto.active())
                .build();

        Employee saved = repository.save(employee);
        return new EmployeeResponseDTO(saved);
    }

    /**
     * Lista todos os funcionários cadastrados, convertendo cada entidade
     * em seu respectivo DTO de resposta através de Streams.
     */
    public List<EmployeeResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(EmployeeResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um funcionário específico pelo ID, lançando uma exceção
     * caso o registro não seja encontrado no banco de dados.
     */
    public EmployeeResponseDTO findById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com o ID: " + id));
        return new EmployeeResponseDTO(employee);
    }

    /**
     * Atualiza os dados de um funcionário existente após localizá-lo por ID,
     * persistindo as alterações e retornando o DTO atualizado.
     */
    @Transactional
    public EmployeeResponseDTO update(Long id, EmployeeRequestDTO dto) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com o ID: " + id));

        employee.setName(dto.name());
        employee.setCpf(dto.cpf());
        employee.setEmail(dto.email());
        employee.setPhone(dto.phone());
        employee.setPasswordHash(dto.password());
        employee.setRole(Role.valueOf(dto.role()));
        employee.setCommissionPercentage(dto.commissionPercentage());
        employee.setActive(dto.active());

        Employee updated = repository.save(employee);
        return new EmployeeResponseDTO(updated);
    }

    /**
     * Exclui um funcionário do sistema após verificar se o ID existe,
     * lançando uma exceção caso contrário.
     */
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Funcionário não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}