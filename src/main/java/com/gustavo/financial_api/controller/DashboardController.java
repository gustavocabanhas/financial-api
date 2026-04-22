package com.gustavo.financial_api.controller;

import com.gustavo.financial_api.dto.BalanceDTO;
import com.gustavo.financial_api.model.Transaction;
import com.gustavo.financial_api.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class DashboardController {

    private final TransactionService transactionService;

    public DashboardController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Boolean clear,
            Model model) {

        LocalDate today = LocalDate.now();
        LocalDate displayStart = startDate;
        LocalDate displayEnd = endDate;

        // Regra de Abertura: Se não tem data e não é comando de limpar, carrega Hoje
        if (startDate == null && endDate == null && (clear == null || !clear)) {
            displayStart = today;
            displayEnd = today;
        }

        // Query: Se as datas forem nulas (após limpar), usamos hoje por baixo dos panos pra alimentar o BI
        LocalDateTime qStart = (displayStart != null) ? displayStart.atStartOfDay() : today.atStartOfDay();
        LocalDateTime qEnd = (displayEnd != null) ? displayEnd.atTime(LocalTime.MAX) : today.atTime(LocalTime.MAX);

        // Busca BI (Gráfico) e Busca Tabela (Paginação)
        List<Transaction> allFiltered = transactionService.getTransactionsBetween(type, qStart, qEnd);
        Page<Transaction> pageResult = transactionService.getTransactionsBetweenPaginated(type, qStart, qEnd, page, size);

        model.addAttribute("transactionList", pageResult.getContent());
        model.addAttribute("transactionsJson", allFiltered);
        model.addAttribute("balance", TransactionService.calculateBalance(allFiltered));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("type", type);
        model.addAttribute("startDate", displayStart);
        model.addAttribute("endDate", displayEnd);

        return "dashboard";
    }
}