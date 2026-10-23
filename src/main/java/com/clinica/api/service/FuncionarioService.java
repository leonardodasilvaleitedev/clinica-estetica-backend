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

    // Injeção de dependência do repositório de funcionários via construtor
    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    /**
     * Cadastra um novo funcionário utilizando o padrão Builder para instanciar a entidade,
     * convertendo a String do cargo para o Enum correspondente e retornando o DTO de resposta.
     */
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

    /**
     * Lista todos os funcionários cadastrados, convertendo cada entidade
     * em seu respectivo DTO de resposta através de Streams.
     */
    public List<FuncionarioResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(FuncionarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um funcionário específico pelo ID, lançando uma exceção
     * caso o registro não seja encontrado no banco de dados.
     */
    public FuncionarioResponseDTO buscarPorId(Long id) {
        Funcionario funcionario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com o ID: " + id));
        return new FuncionarioResponseDTO(funcionario);
    }

    /**
     * Atualiza os dados de um funcionário existente após localizá-lo por ID,
     * persistindo as alterações e retornando o DTO atualizado.
     */
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

    /**
     * Exclui um funcionário do sistema após verificar se o ID existe,
     * lançando uma exceção caso contrário.
     */
    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Funcionário não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}