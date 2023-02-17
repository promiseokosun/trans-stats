package com.seerbit.transstats.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String status;
    private String code;
    private String message;
    private Object data;
    @JsonIgnore
    private HttpStatus httpStatus;
}
