package com.clinica.api.controller;

import com.clinica.api.config.security.JwtTokenService;
import com.clinica.api.config.security.dto.LoginRequestDTO;
import com.clinica.api.config.security.dto.TokenResponseDTO;
import com.clinica.api.domain.model.Funcionario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    // Endpoint rápido para verificar se a rota pública responde
    @GetMapping("/teste")
    public ResponseEntity<String> teste() {
        return ResponseEntity.ok("Rota pública de autenticação funcionando corretamente!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO dto) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
            Authentication auth = this.authenticationManager.authenticate(usernamePassword);

            Funcionario funcionario = (Funcionario) auth.getPrincipal();
            String token = jwtTokenService.gerarToken(funcionario);

            return ResponseEntity.ok(new TokenResponseDTO(
                    token,
                    "Bearer",
                    funcionario.getNome(),
                    funcionario.getCargo().name()
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erro: E-mail ou senha inválidos.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro no servidor: " + e.getMessage());
        }
    }
}