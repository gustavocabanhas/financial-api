package com.gustavo.financial_api.controller;

import com.gustavo.financial_api.dto.BalanceDTO;
import com.gustavo.financial_api.dto.TransactionDTO;
import com.gustavo.financial_api.model.Transaction;
import com.gustavo.financial_api.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO dto) {
        Transaction novaTransacao = transactionService.createTransaction(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTransacao);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO dto) {
        Transaction transacaoAtualizada = transactionService.updateTransaction(id, dto);
        return ResponseEntity.ok(transacaoAtualizada);
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceDTO> getBalance() {
        BalanceDTO balance = transactionService.calculateBalance();
        return ResponseEntity.ok(balance);
    }
}