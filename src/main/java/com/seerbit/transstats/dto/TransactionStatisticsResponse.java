package com.seerbit.transstats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatisticsResponse {
    private String sum;
    private String avg;
    private String min;
    private String max;
    private int count;
}
