package com.clinica.api.dto.finance;

import com.clinica.api.domain.enums.TransactionType;
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
@Builder
public class TransactionRequestDTO {

    // Descrição obrigatória da transação financeira
    @NotBlank(message = "A descrição é obrigatória")
    private String description;

    // Valor obrigatório que deve ser estritamente superior a zero (positivo)
    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser superior a zero")
    private BigDecimal amount;

    // Tipo obrigatório da transação (ex: receita ou despesa)
    @NotNull(message = "O tipo de transação é obrigatório")
    private TransactionType type;

    // Data em que a transação ocorreu (obrigatória)
    @NotNull(message = "A data da transação é obrigatória")
    private LocalDate transactionDate;

    // Forma de pagamento utilizada
    private String paymentMethod;

    // Data e hora em que o pagamento foi efetivado
    private LocalDateTime paymentDate;

    // ID do agendamento vinculado a esta transação financeira
    private Long appointmentId;
}