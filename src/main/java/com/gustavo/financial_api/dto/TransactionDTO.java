package com.gustavo.financial_api.dto;

import lombok.Data;

@Data
public class TransactionDTO {

    private Double amount;

    private String type;

    private String status;

    private String description;

    private String userEmail;
}