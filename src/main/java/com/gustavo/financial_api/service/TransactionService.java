package com.gustavo.financial_api.service;

import com.gustavo.financial_api.clients.NotificationClient;
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
    private final NotificationClient notificationClient;

    // Construtor corrigido para evitar o erro de "assigned to itself"
    public TransactionService(TransactionRepository transactionRepository, NotificationClient notificationClient) {
        this.transactionRepository = transactionRepository;
        this.notificationClient = notificationClient;
    }

    public Transaction createTransaction(TransactionDTO dto) {
        Transaction transaction = Transaction.builder()
                .amount(dto.getAmount())
                .type(dto.getType())
                .status(dto.getStatus() != null ? dto.getStatus() : "PENDING")
                .userEmail(dto.getUserEmail()) // <-- Salva o e-mail na transação
                .createdAt(LocalDateTime.now())
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        String mensagem = "Nova transação de " + savedTransaction.getType() + " no valor de R$ " + savedTransaction.getAmount();

        // AGORA FICOU DINÂMICO:
        notificationClient.solicitarNotificacao(dto.getUserEmail(), mensagem);

        return savedTransaction;
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