package com.clinica.api.dto.finance;

import com.clinica.api.domain.entity.FinancialTransaction;
import com.clinica.api.domain.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO {

    private Long id;
    private String description;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDate transactionDate;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private Long appointmentId;

    /**
     * Método estático que converte uma entidade FinancialTransaction
     * em um TransactionResponseDTO utilizando o padrão Builder.
     */
    public static TransactionResponseDTO fromEntity(FinancialTransaction transaction) {
        return TransactionResponseDTO.builder()
                .id(transaction.getId())
                .description(transaction.getDescription())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .transactionDate(transaction.getTransactionDate())
                .paymentMethod(transaction.getPaymentMethod())
                .paymentDate(transaction.getPaymentDate())
                .appointmentId(transaction.getAppointmentId())
                .build();
    }
}