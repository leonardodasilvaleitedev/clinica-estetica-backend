package com.clinica.api.service;

import com.clinica.api.domain.model.Funcionario;
import com.clinica.api.domain.model.Insumo;
import com.clinica.api.domain.model.UsoInsumo;
import com.clinica.api.dto.estoque.InsumoDTO;
import com.clinica.api.dto.estoque.RegistrarUsoInsumoDTO;
import com.clinica.api.dto.estoque.UsoInsumoResponseDTO;
import com.clinica.api.repository.FuncionarioRepository;
import com.clinica.api.repository.InsumoRepository;
import com.clinica.api.repository.UsoInsumoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstoqueService {

    private final InsumoRepository insumoRepository;
    private final UsoInsumoRepository usoInsumoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public EstoqueService(InsumoRepository insumoRepository, UsoInsumoRepository usoInsumoRepository, FuncionarioRepository funcionarioRepository) {
        this.insumoRepository = insumoRepository;
        this.usoInsumoRepository = usoInsumoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @Transactional
    public InsumoDTO cadastroInsumo(InsumoDTO dto){
        Insumo insumo = new Insumo(
        dto.nome(),
        dto.marca(),
        dto.unidadeMedida(),
        dto.quantidadeEstoque(),
        dto.estoqueMinimo()
        );
        Insumo salvo = insumoRepository.save(insumo);
        return new InsumoDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getMarca(),
                salvo.getUnidadeMedida(),
                salvo.getQuantidadeEstoque(),
                salvo.getEstoqueMinimo()
        );
    }

    public List<InsumoDTO> listaInsumos(){
        return insumoRepository.findAll().stream().map(i -> new InsumoDTO(
                i.getId(),
                i.getNome(),
                i.getMarca(),
                i.getUnidadeMedida(),
                i.getQuantidadeEstoque(),
                i.getEstoqueMinimo()
        )).toList();
    }

    @Transactional
    public UsoInsumoResponseDTO registrarUso(RegistrarUsoInsumoDTO dto){
        Funcionario funcionario = funcionarioRepository.findById(dto.funcionarioId())
                .orElseThrow(()-> new IllegalArgumentException("Funcionario não encontrado com ID:" + dto.funcionarioId()));
        Insumo insumo = insumoRepository.findById(dto.insumoId())
                .orElseThrow(()-> new IllegalArgumentException("Insumo não encontrado com ID:" + dto.insumoId()));

        // Abate do estoque (dispara exceção se a quantidade for insuficiente)
        insumo.abaterEstoque(dto.quantidadeUsada());
        insumoRepository.save(insumo);

        // Registro do consumo
        UsoInsumo uso = new UsoInsumo();
        uso.setFuncionario(funcionario);
        uso.setInsumo(insumo);
        uso.setQuantidadeUsada(dto.quantidadeUsada());
        uso.setObservacao(dto.observacao());

        UsoInsumo salvo = usoInsumoRepository.save(uso);

        return new UsoInsumoResponseDTO(
                salvo.getId(),
                salvo.getFuncionario().getNome(),
                salvo.getInsumo().getNome(),
                salvo.getQuantidadeUsada(),
                salvo.getDataUso(),
                salvo.getObservacao()
        );
    }

    public List<UsoInsumoResponseDTO> listarUsoPorFuncionario(Long funcionarioId){
        return usoInsumoRepository.findByFuncionarioId(funcionarioId).stream()
                .map(u-> new UsoInsumoResponseDTO(
                        u.getId(),
                        u.getFuncionario().getNome(),
                        u.getInsumo().getNome(),
                        u.getQuantidadeUsada(),
                        u.getDataUso(),
                        u.getObservacao()
                )).toList();
    }

}