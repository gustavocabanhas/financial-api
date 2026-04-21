package com.gustavo.financial_api.service;

import com.gustavo.financial_api.clients.NotificationClient;
import com.gustavo.financial_api.dto.BalanceDTO;
import com.gustavo.financial_api.dto.TransactionDTO;
import com.gustavo.financial_api.model.Transaction;
import com.gustavo.financial_api.repository.TransactionRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unused") // Silencia avisos de métodos usados apenas via API
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final NotificationClient notificationClient;

    public TransactionService(TransactionRepository transactionRepository, NotificationClient notificationClient) {
        this.transactionRepository = transactionRepository;
        this.notificationClient = notificationClient;
    }

    // --- BUSCAS ---
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsBetween(String type, LocalDateTime start, LocalDateTime end) {
        if (type != null && !type.isEmpty()) {
            return transactionRepository.findByTypeAndCreatedAtBetween(type, start, end);
        }
        return transactionRepository.findByCreatedAtBetween(start, end);
    }

    public Page<Transaction> getTransactionsBetweenPaginated(String type, LocalDateTime start, LocalDateTime end, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        if (type != null && !type.isEmpty()) {
            return transactionRepository.findByTypeAndCreatedAtBetween(type, start, end, pageable);
        }
        return transactionRepository.findByCreatedAtBetween(start, end, pageable);
    }

    // --- CÁLCULO DE SALDO ---

    // Para a API: Calcula o saldo total de tudo no banco
    public BalanceDTO calculateBalance() {
        return calculateBalance(transactionRepository.findAll());
    }

    // Para o Dashboard: Estático para processar a lista filtrada sem avisos da IDE
    public static BalanceDTO calculateBalance(List<Transaction> transactions) {
        double receitas = 0.0;
        double despesas = 0.0;
        if (transactions != null) {
            for (Transaction t : transactions) {
                if (t.getAmount() != null) {
                    if ("RECEITA".equalsIgnoreCase(t.getType())) receitas += t.getAmount();
                    else if ("DESPESA".equalsIgnoreCase(t.getType())) despesas += t.getAmount();
                }
            }
        }
        return new BalanceDTO(receitas, despesas, receitas - despesas);
    }

    // --- ESCRITA E MANUTENÇÃO ---
    public Transaction createTransaction(TransactionDTO dto) {
        Transaction t = Transaction.builder()
                .amount(dto.getAmount())
                .type(dto.getType())
                .status(dto.getStatus() != null ? dto.getStatus() : "COMPLETED")
                .userEmail(dto.getUserEmail())
                .createdAt(LocalDateTime.now())
                .build();
        Transaction saved = transactionRepository.save(t);
        try {
            notificationClient.solicitarNotificacao(dto.getUserEmail(), "Nova transação registrada.");
        } catch (Exception e) {
            System.err.println("Aviso: Serviço de notificação offline.");
        }
        return saved;
    }

    public List<Transaction> createTransactionsBulk(List<TransactionDTO> dtos) {
        return dtos.stream().map(this::createTransaction).collect(Collectors.toList());
    }

    public Transaction updateTransaction(Long id, TransactionDTO dto) {
        Transaction existente = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
        if (dto.getAmount() != null) existente.setAmount(dto.getAmount());
        if (dto.getType() != null) existente.setType(dto.getType());
        return transactionRepository.save(existente);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}