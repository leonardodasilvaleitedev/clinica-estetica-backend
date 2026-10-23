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

    // Descrição obrigatória da transação financeira
    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    // Valor obrigatório que deve ser estritamente superior a zero (positivo)
    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser superior a zero")
    private BigDecimal valor;

    // Tipo obrigatório da transação (ex: receita ou despesa)
    @NotNull(message = "O tipo de transação é obrigatório")
    private TipoTransacao tipo;

    // Data em que a transação ocorreu (obrigatória)
    @NotNull(message = "A data da transação é obrigatória")
    private LocalDate dataTransacao;

    // Forma de pagamento utilizada
    private String formaPagamento;

    // Data e hora em que o pagamento foi efetivado
    private LocalDateTime dataPagamento;

    // ID do agendamento vinculado a esta transação financeira
    private Long agendamentoId;
}