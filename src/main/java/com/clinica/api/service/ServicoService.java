package com.clinica.api.service;

import com.clinica.api.domain.model.Servico;
import com.clinica.api.dto.servicos.ServicoRequestDTO;
import com.clinica.api.dto.servicos.ServicoResponseDTO;
import com.clinica.api.repository.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoService {

    private final ServicoRepository repository;

    public ServicoService(ServicoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ServicoResponseDTO criar(ServicoRequestDTO dto) {
        Servico servico = Servico.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .duracaoMinutos(dto.duracaoMinutos())
                .precoBase(dto.preco())
                .ativo(dto.ativo())
                .build();

        Servico salvo = repository.save(servico);
        return new ServicoResponseDTO(salvo);
    }

    public List<ServicoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(ServicoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ServicoResponseDTO buscarPorId(Long id) {
        Servico servico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado com o ID: " + id));
        return new ServicoResponseDTO(servico);
    }

    @Transactional
    public ServicoResponseDTO atualizar(Long id, ServicoRequestDTO dto) {
        Servico servico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado com o ID: " + id));

        servico.setNome(dto.nome());
        servico.setDescricao(dto.descricao());
        servico.setDuracaoMinutos(dto.duracaoMinutos());
        servico.setPrecoBase(dto.preco());
        servico.setAtivo(dto.ativo());

        Servico atualizado = repository.save(servico);
        return new ServicoResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Serviço não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}