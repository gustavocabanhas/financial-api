package com.gustavo.financial_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BalanceDTO {
    private Double totalReceitas;
    private Double totalDespesas;
    private Double saldoTotal;
}