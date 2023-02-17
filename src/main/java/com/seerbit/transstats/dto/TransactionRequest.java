package com.seerbit.transstats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    @NotBlank(message = "amount is required")
    private String amount;
    @NotBlank(message = "timestamp is required")
    private String timestamp;
}
