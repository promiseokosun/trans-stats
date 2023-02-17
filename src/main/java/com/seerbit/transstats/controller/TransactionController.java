package com.seerbit.transstats.controller;

import com.seerbit.transstats.dto.ApiResponse;
import com.seerbit.transstats.dto.TransactionRequest;
import com.seerbit.transstats.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;

import static com.seerbit.transstats.constants.Constants.LOGGER_STRING_GET;
import static com.seerbit.transstats.constants.Constants.LOGGER_STRING_POST;

@Controller
@Slf4j
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        ApiResponse apiResponse = ApiResponse.getSuccessResponse(transactionService.getAllTransactions(), HttpStatus.OK);
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionRequest dto, HttpServletRequest req) {
        String url = req.getRequestURL().toString();
        ApiResponse apiResponse = transactionService.createTransaction(dto);
        log.info(LOGGER_STRING_POST, url, dto, apiResponse);
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @GetMapping("statistics")
    public ResponseEntity<?> getTransactionStats(HttpServletRequest req) {
        String url = req.getRequestURL().toString();
        ApiResponse apiResponse = ApiResponse.getSuccessResponse(transactionService.getStatistics(LocalDateTime.now(), 30), HttpStatus.OK);
        log.info(LOGGER_STRING_GET, url, apiResponse);
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTransaction() {
        transactionService.deleteAllTransactions();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
