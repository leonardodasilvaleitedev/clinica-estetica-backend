package com.clinica.api.service;

import com.clinica.api.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    // Injeção de dependência do repositório de funcionários via construtor
    public AuthenticationService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Carrega os detalhes do usuário pelo nome de usuário (neste projeto, o e-mail),
     * lançando uma exceção caso o funcionário não seja encontrado no banco de dados.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado com o e-mail: " + username));
    }
}