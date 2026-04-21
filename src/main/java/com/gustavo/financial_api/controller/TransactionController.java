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
    public ResponseEntity<List<Transaction>> createTransactions(@RequestBody List<TransactionDTO> dtos) {
        List<Transaction> novas = transactionService.createTransactionsBulk(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(novas);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceDTO> getBalance() {
        // Agora chama o método de instância que criamos no Service
        return ResponseEntity.ok(transactionService.calculateBalance());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}