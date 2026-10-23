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

    // Injeção de dependência do repositório de serviços via construtor
    public ServicoService(ServicoRepository repository) {
        this.repository = repository;
    }

    /**
     * Cadastra um novo serviço utilizando o padrão Builder para instanciar a entidade
     * com base no DTO de requisição, salvando no banco e retornando o DTO de resposta.
     */
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

    /**
     * Lista todos os serviços cadastrados, convertendo cada entidade
     * em seu respectivo DTO de resposta através de Streams.
     */
    public List<ServicoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(ServicoResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um serviço específico pelo ID, lançando uma exceção
     * caso o registro não seja encontrado no banco de dados.
     */
    public ServicoResponseDTO buscarPorId(Long id) {
        Servico servico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado com o ID: " + id));
        return new ServicoResponseDTO(servico);
    }

    /**
     * Atualiza os dados de um serviço existente após localizá-lo por ID,
     * persistindo as alterações e retornando o DTO atualizado.
     */
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

    /**
     * Exclui um serviço do sistema após verificar se o ID existe,
     * lançando uma exceção caso contrário.
     */
    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Serviço não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}