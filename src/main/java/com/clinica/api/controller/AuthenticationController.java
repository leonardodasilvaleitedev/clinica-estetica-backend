package com.clinica.api.controller;

import com.clinica.api.config.security.JwtTokenService;
import com.clinica.api.config.security.dto.LoginRequestDTO;
import com.clinica.api.config.security.dto.TokenResponseDTO;
import com.clinica.api.domain.entity.Employee;
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
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    /**
     * Endpoint GET de teste para verificar se as rotas públicas respondem corretamente.
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Rota pública de autenticação funcionando corretamente!");
    }

    /**
     * Endpoint POST de login que autentica as credenciais do funcionário
     * e retorna o token JWT juntamente com os dados essenciais de acesso.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO dto) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
            Authentication auth = this.authenticationManager.authenticate(usernamePassword);

            Employee employee = (Employee) auth.getPrincipal();
            String token = jwtTokenService.generateToken(employee);

            return ResponseEntity.ok(new TokenResponseDTO(
                    token,
                    "Bearer",
                    employee.getName(),
                    employee.getRole().name()
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