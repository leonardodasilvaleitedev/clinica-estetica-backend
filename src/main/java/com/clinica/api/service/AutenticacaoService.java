package com.clinica.api.service;

import com.clinica.api.repository.FuncionarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final FuncionarioRepository funcionarioRepository;

    // Injeção de dependência do repositório de funcionários via construtor
    public AutenticacaoService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    /**
     * Carrega os detalhes do usuário pelo nome de usuário (neste projeto, o e-mail),
     * lançando uma exceção caso o funcionário não seja encontrado no banco de dados.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return funcionarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado com o e-mail: " + username));
    }
}