package com.clinica.api.domain.model;

import com.clinica.api.domain.enums.Cargo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "funcionarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Funcionario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(length = 20)
    private String telefone;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, name = "senha_hash")
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cargo cargo;

    @Column(name = "percentual_comissao", nullable = false, precision = 5, scale = 2)
    private BigDecimal percentualComissao;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;

    // --- Métodos da interface UserDetails do Spring Security ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + cargo.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(this.ativo);
    }
}