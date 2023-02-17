package com.seerbit.transstats.exception;

import com.seerbit.transstats.constants.ResponseCodes;
import com.seerbit.transstats.constants.ResponseStatuses;
import com.seerbit.transstats.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.seerbit.transstats.constants.Constants.LOGGER_STRING_GET;

@ControllerAdvice
@Slf4j
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                          HttpHeaders headers,
                                                          HttpStatus status,
                                                          WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String url = ((ServletWebRequest) request).getRequest().getRequestURL().toString();
        ApiResponse apiResponse = new ApiResponse(ResponseCodes.BAD_INPUT_PARAM.getCode(), ResponseStatuses.failed.name(), ResponseCodes.BAD_INPUT_PARAM.getMessage(), errors, HttpStatus.BAD_REQUEST);
        ResponseEntity re = new ResponseEntity<> (apiResponse, apiResponse.getHttpStatus());
        log.error(LOGGER_STRING_GET, url, re);
        return re;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = IOException.class)
    public ResponseEntity<?> handleIOException(IOException ex, HttpServletRequest request) {
        log.error(LOGGER_STRING_GET,  request.getRequestURL() , ex.getMessage());
        ex.printStackTrace();
        ApiResponse apiResponse = new ApiResponse(ResponseCodes.IO_EXCEPTION.getCode(), ResponseStatuses.error.name(),
                ResponseCodes.IO_EXCEPTION.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<> (apiResponse, apiResponse.getHttpStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleException(Exception ex, HttpServletRequest request) {
        log.error(LOGGER_STRING_GET,  request.getRequestURL() , ex.getMessage());
        ex.printStackTrace();
        ApiResponse apiResponse = new ApiResponse(ResponseCodes.INTERNAL_SERVER.getCode(), ResponseStatuses.error.name(),
                ResponseCodes.INTERNAL_SERVER.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<> (apiResponse, apiResponse.getHttpStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException ex, HttpServletRequest request) {
        log.error(LOGGER_STRING_GET, request.getRequestURL(), ex.getMessage());
        ApiResponse apiResponse = new ApiResponse(ex.getCode(), ex.getStatus(), ex.getMessage(), null, ex.getHttpStatus());
        return new ResponseEntity<> (apiResponse, ex.getHttpStatus());
    }

}
