package com.clinica.api.service;

import com.clinica.api.domain.enums.Cargo;
import com.clinica.api.domain.model.Funcionario;
import com.clinica.api.dto.funcionario.FuncionarioRequestDTO;
import com.clinica.api.dto.funcionario.FuncionarioResponseDTO;
import com.clinica.api.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public FuncionarioResponseDTO criar(FuncionarioRequestDTO dto) {
        Funcionario funcionario = Funcionario.builder()
                .nome(dto.nome())
                .cpf(dto.cpf())
                .email(dto.email())
                .telefone(dto.telefone())
                .senha(dto.senha())
                .cargo(Cargo.valueOf(dto.cargo()))
                .percentualComissao(dto.percentualComissao())
                .ativo(dto.ativo())
                .build();

        Funcionario salvo = repository.save(funcionario);
        return new FuncionarioResponseDTO(salvo);
    }

    public List<FuncionarioResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(FuncionarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    public FuncionarioResponseDTO buscarPorId(Long id) {
        Funcionario funcionario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com o ID: " + id));
        return new FuncionarioResponseDTO(funcionario);
    }

    @Transactional
    public FuncionarioResponseDTO atualizar(Long id, FuncionarioRequestDTO dto) {
        Funcionario funcionario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com o ID: " + id));

        funcionario.setNome(dto.nome());
        funcionario.setCpf(dto.cpf());
        funcionario.setEmail(dto.email());
        funcionario.setTelefone(dto.telefone());
        funcionario.setSenha(dto.senha());
        funcionario.setCargo(Cargo.valueOf(dto.cargo()));
        funcionario.setPercentualComissao(dto.percentualComissao());
        funcionario.setAtivo(dto.ativo());

        Funcionario atualizado = repository.save(funcionario);
        return new FuncionarioResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Funcionário não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}