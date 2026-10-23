package com.clinica.api.config.security;

import com.clinica.api.repository.FuncionarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de segurança JWT que intercepta todas as requisições HTTP para validar
 * o token de autenticação do utilizador antes que chegue aos Controllers.
 * Estende OncePerRequestFilter para garantir que executa exatamente uma vez por requisição.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService tokenService;
    private final FuncionarioRepository funcionarioRepository;

    // Injeção de dependências via construtor dos serviços de token e repositório de funcionários
    public JwtAuthenticationFilter(JwtTokenService tokenService, FuncionarioRepository funcionarioRepository) {
        this.tokenService = tokenService;
        this.funcionarioRepository = funcionarioRepository;
    }

    /**
     * Define quais rotas DEVEM SER IGNORADAS pelo filtro (ex: login, registo ou rotas públicas).
     * Evita bloqueios indevidos (erro 403) em endpoints que não exigem autenticação prévia.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/") || (path.equals("/api/funcionarios") && request.getMethod().equalsIgnoreCase("POST"));
    }

    /**
     * Lógica principal executada em cada requisição para extrair, validar e autenticar o token JWT.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var tokenJwt = recuperarToken(request);

        if (tokenJwt != null) {
            try {
                var subject = tokenService.getSubject(tokenJwt);
                var funcionario = funcionarioRepository.findByEmail(subject).orElse(null);

                if (funcionario != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                            funcionario, null, funcionario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Método auxiliar privado para ler o cabeçalho HTTP "Authorization"
     * e remover o prefixo "Bearer " para isolar apenas o código do token JWT.
     */
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "").trim();
        }
        return null;
    }
}