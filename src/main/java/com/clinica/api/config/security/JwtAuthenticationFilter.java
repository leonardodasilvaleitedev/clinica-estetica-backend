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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService tokenService;
    private final FuncionarioRepository funcionarioRepository;

    public JwtAuthenticationFilter(JwtTokenService tokenService, FuncionarioRepository funcionarioRepository) {
        this.tokenService = tokenService;
        this.funcionarioRepository = funcionarioRepository;
    }

    // IGNORA O FILTRO EM ROTAS PÚBLICAS PARA EVITAR 403 INDEVIDO
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/") || (path.equals("/api/funcionarios") && request.getMethod().equalsIgnoreCase("POST"));
    }

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

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "").trim();
        }
        return null;
    }
}