package com.seerbit.transstats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    @NotBlank(message = "amount is required")
    private String amount;
    @DateTimeFormat()
    @NotBlank(message = "amount is required")
    private String timeStamp;
}
