package com.gustavo.financial_api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationLogDTO {
    private Long id;
    private String message;
    private String recipient;
    private LocalDateTime scheduleDate;
    private String status;
}