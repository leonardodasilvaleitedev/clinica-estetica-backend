package com.clinica.api.dto.financeiro;

import com.clinica.api.domain.enums.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransacaoRequestDTO {

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser superior a zero")
    private BigDecimal valor;

    @NotNull(message = "O tipo de transação é obrigatório")
    private TipoTransacao tipo;

    @NotNull(message = "A data da transação é obrigatória")
    private LocalDate dataTransacao;

    private String formaPagamento;
    private LocalDateTime dataPagamento;
    private Long agendamentoId;
}