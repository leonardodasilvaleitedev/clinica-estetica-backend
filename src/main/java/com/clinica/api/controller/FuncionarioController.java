package com.clinica.api.controller;

import com.clinica.api.domain.enums.Cargo;
import com.clinica.api.dto.funcionario.FuncionarioRequestDTO;
import com.clinica.api.dto.funcionario.FuncionarioResponseDTO;
import com.clinica.api.domain.model.Funcionario;
import com.clinica.api.repository.FuncionarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioController(FuncionarioRepository funcionarioRepository, PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> criar(@RequestBody @Valid FuncionarioRequestDTO dto) {
        var funcionario = Funcionario.builder()
                .nome(dto.nome())
                .cpf(dto.cpf())
                .email(dto.email())
                .telefone(dto.telefone())
                .senha(passwordEncoder.encode(dto.senha())) // Codifica a senha com BCrypt e usa .senha()
                .cargo(Cargo.valueOf(dto.cargo().toUpperCase()))
                .percentualComissao(dto.percentualComissao())
                .ativo(dto.ativo() != null ? dto.ativo() : true)
                .build();

        Funcionario salvo = funcionarioRepository.save(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new FuncionarioResponseDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> listar() {
        var funcionarios = funcionarioRepository.findAll()
                .stream()
                .map(FuncionarioResponseDTO::new)
                .toList();
        return ResponseEntity.ok(funcionarios);
    }
}
