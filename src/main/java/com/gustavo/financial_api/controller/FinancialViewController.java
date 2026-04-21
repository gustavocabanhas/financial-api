package com.gustavo.financial_api.controller;

import com.gustavo.financial_api.model.Transaction;
import com.gustavo.financial_api.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class FinancialViewController {

    private final TransactionService transactionService;

    public FinancialViewController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public String showDashboard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        // DEFINE "TODAY" COMO PADRÃO AUTOMATICAMENTE
        LocalDate hoje = LocalDate.now();
        LocalDate dataInicio = (startDate != null) ? startDate : hoje;
        LocalDate dataFim = (endDate != null) ? endDate : hoje;

        LocalDateTime start = dataInicio.atStartOfDay();
        LocalDateTime end = dataFim.atTime(23, 59, 59);

        // Busca os dados filtrados
        List<Transaction> all = transactionService.getTransactionsBetween(type, start, end);
        Page<Transaction> paged = transactionService.getTransactionsBetweenPaginated(type, start, end, page, size);

        model.addAttribute("balance", TransactionService.calculateBalance(all));
        model.addAttribute("transactions", all);
        model.addAttribute("transactionList", paged.getContent());

        // Passamos as datas de volta para o HTML (como LocalDate)
        model.addAttribute("startDate", dataInicio);
        model.addAttribute("endDate", dataFim);
        model.addAttribute("type", type);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paged.getTotalPages());
        model.addAttribute("pageSize", size);

        return "dashboard";
    }
}