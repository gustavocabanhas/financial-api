package com.gustavo.financial_api.service;

import com.gustavo.financial_api.dto.BalanceDTO;
import com.gustavo.financial_api.dto.TransactionDTO;
import com.gustavo.financial_api.model.Transaction;
import com.gustavo.financial_api.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(TransactionDTO dto) {
        Transaction transaction = Transaction.builder()
                .amount(dto.getAmount())
                .type(dto.getType())
                .status(dto.getStatus() != null ? dto.getStatus() : "PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public void deleteTransaction(Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Transação não encontrada para deletar!");
        }
    }

    public Transaction updateTransaction(Long id, TransactionDTO dto) {
        Transaction transacaoAntiga = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada para atualizar!"));

        if (dto.getAmount() != null) transacaoAntiga.setAmount(dto.getAmount());
        if (dto.getType() != null) transacaoAntiga.setType(dto.getType());
        if (dto.getStatus() != null) transacaoAntiga.setStatus(dto.getStatus());

        return transactionRepository.save(transacaoAntiga);
    }

    public BalanceDTO calculateBalance() {
        List<Transaction> transactions = transactionRepository.findAll();

        double receitas = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("RECEITA"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double despesas = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("DESPESA"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        return BalanceDTO.builder()
                .totalReceitas(receitas)
                .totalDespesas(despesas)
                .saldoTotal(receitas - despesas)
                .build();
    }
}