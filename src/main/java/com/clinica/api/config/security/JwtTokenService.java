package com.clinica.api.config.security;

import com.clinica.api.domain.model.Funcionario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenService {

    @Value("${jwt.secret:umaChaveSecretaMuitoLongaEExtremamenteSeguraParaClinicaEstetica123456}")
    private String secret;

    @Value("${jwt.expiration:28800000}") // 8 horas em milissegundos
    private Long expiration;

    // Converte a chave secreta em um formato criptográfico seguro para assinatura do token
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }


    /**
     * Gera o token JWT para o funcionário autenticado, incluindo dados (claims)
     * como e-mail, id, nome e cargo, com validade de 8 horas.
     */
    public String gerarToken(Funcionario funcionario) {
        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + expiration);

        return Jwts.builder()
                .setSubject(funcionario.getEmail())
                .claim("id", funcionario.getId())
                .claim("nome", funcionario.getNome())
                .claim("role", funcionario.getCargo().name())
                .setIssuedAt(agora)
                .setExpiration(dataExpiracao)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * Valida o token JWT e extrai o "subject" (e-mail) armazenado no seu interior.
     */
    public String getSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Verifica se o token JWT é válido e se não expirou ou foi adulterado.
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}