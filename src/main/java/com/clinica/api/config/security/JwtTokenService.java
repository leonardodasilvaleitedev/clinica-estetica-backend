package com.clinica.api.config.security;

import com.clinica.api.domain.entity.Employee;
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
     * como e-mail, id, nome e role, com validade de 8 horas.
     */
    public String generateToken(Employee employee) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(employee.getEmail())
                .claim("id", employee.getId())
                .claim("name", employee.getName())
                .claim("role", employee.getRole().name())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
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