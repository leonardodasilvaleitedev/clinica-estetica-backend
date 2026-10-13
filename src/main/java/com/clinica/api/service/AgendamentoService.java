package com.clinica.api.service;

import com.clinica.api.domain.enums.StatusAgendamento;
import com.clinica.api.domain.model.Agendamento;
import com.clinica.api.domain.model.Cliente;
import com.clinica.api.domain.model.Funcionario;
import com.clinica.api.domain.model.Servico;
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

        // Cálculo do horário final
        int duracaoMinutos = (servico.getDuracaoMinutos() != null) ? servico.getDuracaoMinutos() : 60;
        LocalDateTime dataHoraInicio = dto.dataHoraInicio();
        LocalDateTime dataHoraFim = dataHoraInicio.plusMinutes(duracaoMinutos);

        // Validação de conflito de agenda
        boolean existeConflito = agendamentoRepository.existeConflitoHorario(
                dto.funcionarioId(), dataHoraInicio, dataHoraFim);

        if (existeConflito) {
            throw new IllegalArgumentException("O profissional já possui um agendamento neste intervalo de horário.");
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setFuncionario(funcionario);
        agendamento.setDataHoraInicio(dataHoraInicio);
        agendamento.setDataHoraFim(dataHoraFim);
        agendamento.setObservacao(dto.observacao());
        agendamento.setCriadoEm(LocalDateTime.now());
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        if (dto.valorCobrado() != null) {
            agendamento.setValorCobrado(dto.valorCobrado());
        } else {
            agendamento.setValorCobrado(servico.getPrecoBase());
        }

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        return new AgendamentoResponseDTO(agendamentoSalvo);
    }

    @Transactional(readOnly = true)
    public List<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAllComRelacionamentos()
                .stream()
                .map(AgendamentoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public AgendamentoResponseDTO buscarPorIdDto(Long id) {
        Agendamento agendamento = agendamentoRepository.findByIdComRelacionamentos(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com ID: " + id));
        return new AgendamentoResponseDTO(agendamento);
    }

    @Transactional
    public AgendamentoResponseDTO atualizarStatus(Long id, StatusAgendamento novoStatus) {
        Agendamento agendamento = agendamentoRepository.findByIdComRelacionamentos(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com ID: " + id));

        agendamento.setStatus(novoStatus);
        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamento);
        return new AgendamentoResponseDTO(agendamentoAtualizado);
    }
}