package com.gustavo.financial_api.repository;

import com.gustavo.financial_api.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Busca paginada por período
    Page<Transaction> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    // Busca lista completa por período (para o BI/Gráfico)
    List<Transaction> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    // Busca Paginada com Datas e Tipo
    Page<Transaction> findByTypeAndCreatedAtBetween(String type, LocalDateTime start, LocalDateTime end, Pageable pageable);

    // Busca Lista para BI com Datas e Tipo
    List<Transaction> findByTypeAndCreatedAtBetween(String type, LocalDateTime start, LocalDateTime end);
}