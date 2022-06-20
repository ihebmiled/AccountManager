package org.account.manager.tech.api.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {
    private Float amount;
    private String operation;
    private LocalDateTime dateTime;
    private Float previousBalance;
    private Float newBalance;
}
