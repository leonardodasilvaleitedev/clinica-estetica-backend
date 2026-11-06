package com.clinica.api.repository;

import com.clinica.api.domain.entity.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Herda automaticamente os métodos CRUD padrão para a entidade FinancialTransaction
@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {}