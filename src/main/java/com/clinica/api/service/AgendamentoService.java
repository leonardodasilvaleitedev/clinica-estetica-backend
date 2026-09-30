package com.clinica.api.service;

import com.clinica.api.domain.model.Agendamento;
import com.clinica.api.domain.model.Cliente;
import com.clinica.api.domain.model.Funcionario;
import com.clinica.api.domain.model.Servico;
import com.clinica.api.domain.enums.StatusAgendamento;
import com.clinica.api.dto.Agendamento.AgendamentoRequestDTO;
import com.clinica.api.dto.Agendamento.AgendamentoResponseDTO;
import com.clinica.api.repository.AgendamentoRepository;
import com.clinica.api.repository.ClienteRepository;
import com.clinica.api.repository.FuncionarioRepository;
import com.clinica.api.repository.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final ServicoRepository servicoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              ClienteRepository clienteRepository,
                              ServicoRepository servicoRepository,
                              FuncionarioRepository funcionarioRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.servicoRepository = servicoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @Transactional
    public AgendamentoResponseDTO criarComDto(AgendamentoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + dto.clienteId()));

        Servico servico = servicoRepository.findById(dto.servicoId())
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado com ID: " + dto.servicoId()));

        Funcionario funcionario = funcionarioRepository.findById(dto.funcionarioId())
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com ID: " + dto.funcionarioId()));

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setFuncionario(funcionario);
        agendamento.setDataHoraInicio(dto.dataHoraInicio());
        agendamento.setObservacao(dto.observacao());
        agendamento.setCriadoEm(LocalDateTime.now());

        if (agendamento.getStatus() == null) {
            agendamento.setStatus(StatusAgendamento.AGENDADO);
        }

        if (dto.valorCobrado() != null) {
            agendamento.setValorCobrado(dto.valorCobrado());
        } else {
            agendamento.setValorCobrado(servico.getPrecoBase());
        }

        if (servico.getDuracaoMinutos() != null) {
            agendamento.setDataHoraFim(dto.dataHoraInicio().plusMinutes(servico.getDuracaoMinutos()));
        } else {
            agendamento.setDataHoraFim(dto.dataHoraInicio().plusHours(1));
        }

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);

        return new AgendamentoResponseDTO(agendamentoSalvo);
    }

    // --- MÉTODO PARA LISTAR TODOS OS AGENDAMENTOS ---
    public List<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAll()
                .stream()
                .map(AgendamentoResponseDTO::new)
                .toList();
    }

}