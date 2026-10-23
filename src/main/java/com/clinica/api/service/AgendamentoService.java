package com.clinica.api.service;

import com.clinica.api.domain.model.Agendamento;
import com.clinica.api.domain.enums.StatusAgendamento;
import com.clinica.api.domain.enums.TipoTransacao;
import com.clinica.api.domain.model.Cliente;
import com.clinica.api.domain.model.Funcionario;
import com.clinica.api.domain.model.Servico;
import com.clinica.api.dto.Agendamento.AgendamentoRequestDTO;
import com.clinica.api.dto.Agendamento.AgendamentoResponseDTO;
import com.clinica.api.dto.estoque.RegistrarUsoInsumoDTO;
import com.clinica.api.dto.financeiro.TransacaoRequestDTO;
import com.clinica.api.repository.AgendamentoRepository;
import com.clinica.api.repository.ClienteRepository;
import com.clinica.api.repository.FuncionarioRepository;
import com.clinica.api.repository.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final ServicoRepository servicoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final TransacaoFinanceiraService transacaoFinanceiraService;
    private final EstoqueService estoqueService;

    // Injeção de dependências via construtor
    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              ClienteRepository clienteRepository,
                              ServicoRepository servicoRepository,
                              FuncionarioRepository funcionarioRepository,
                              TransacaoFinanceiraService transacaoFinanceiraService,
                              EstoqueService estoqueService ) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.servicoRepository = servicoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.transacaoFinanceiraService = transacaoFinanceiraService;
        this.estoqueService = estoqueService;
    }

    /**
     * Cria um novo agendamento validando a existência do cliente, serviço e funcionário,
     * calculando valores e definindo a data/hora de término com base na duração do serviço.
     */
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

        // Define o status padrão como AGENDADO caso não venha preenchido
        if (agendamento.getStatus() == null) {
            agendamento.setStatus(StatusAgendamento.AGENDADO);
        }

        // Define o valor cobrado (se não informado, assume o preço base do serviço)
        if (dto.valorCobrado() != null) {
            agendamento.setValorCobrado(dto.valorCobrado());
        } else {
            agendamento.setValorCobrado(servico.getPrecoBase());
        }

        // Calcula a data/hora de término com base na duração em minutos do serviço
        if (servico.getDuracaoMinutos() != null) {
            agendamento.setDataHoraFim(dto.dataHoraInicio().plusMinutes(servico.getDuracaoMinutos()));
        } else {
            agendamento.setDataHoraFim(dto.dataHoraInicio().plusHours(1));
        }

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);

        return new AgendamentoResponseDTO(agendamentoSalvo);
    }

    // MÉTODO PARA LISTAR TODOS OS AGENDAMENTOS
    public List<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAll().stream()
                .map(AgendamentoResponseDTO::new)
                .toList();
    }


    /**
     * Conclui um agendamento, gera automaticamente a transação financeira de receita
     * e processa a baixa dinâmica de insumos utilizados no estoque.
     */
    // MÉTODO PARA CONCLUIR AGENDAMENTO E GERAR TRANSAÇÃO FINANCEIRA E BAIXA DE ESTOQUE FLEXÍVEL
    @Transactional
    public AgendamentoResponseDTO concluirAgendamento(Long id, List<RegistrarUsoInsumoDTO> insumosUsados) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com ID: " + id));

        agendamento.setStatus(StatusAgendamento.CONCLUIDO);
        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamento);

        // Gerar transação financeira automaticamente
        TransacaoRequestDTO transacaoDTO = TransacaoRequestDTO.builder()
                .descricao("Atendimento: " + agendamento.getServico().getNome() + " - Cliente: " + agendamento.getCliente().getNome())
                .valor(agendamento.getValorCobrado())
                .tipo(TipoTransacao.RECEITA)
                .formaPagamento("PIX")
                .dataTransacao(LocalDate.now())
                .dataPagamento(LocalDateTime.now())
                .agendamentoId(agendamento.getId())
                .build();

        transacaoFinanceiraService.criar(transacaoDTO);

        // Processar a lista dinâmica de insumos enviados pelo front-end
        if (insumosUsados != null && !insumosUsados.isEmpty()) {
            Long funcionarioId = agendamento.getFuncionario().getId();

            for (RegistrarUsoInsumoDTO item : insumosUsados) {
                RegistrarUsoInsumoDTO usoDTO = new RegistrarUsoInsumoDTO(
                        funcionarioId,
                        item.insumoId(),
                        item.quantidadeUsada(),
                        item.observacao() != null ? item.observacao() : "Baixa automática via conclusão do agendamento #" + agendamento.getId()
                );

                estoqueService.registrarUso(usoDTO);
            }
        }

        return new AgendamentoResponseDTO(agendamentoAtualizado);
    }
}