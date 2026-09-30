package com.clinica.api.service;

import com.clinica.api.repository.FuncionarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final FuncionarioRepository funcionarioRepository;

    public AutenticacaoService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return funcionarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado com o e-mail: " + username));
    }
}