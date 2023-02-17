package com.seerbit.transstats.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seerbit.transstats.constants.ResponseCodes;
import com.seerbit.transstats.constants.ResponseStatuses;
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

    public static ApiResponse getSuccessResponse(Object data, HttpStatus httpStatus) {
        return new ApiResponse(ResponseStatuses.success.name(), ResponseCodes.REQUEST_SUCCESSFUL.getCode(), ResponseCodes.REQUEST_SUCCESSFUL.getMessage(), data, httpStatus);
    }

    public static ApiResponse getFailedResponse(String code, String message, HttpStatus httpStatus) {
        return new ApiResponse(ResponseStatuses.failed.name(), code, message, null, httpStatus);
    }
}
