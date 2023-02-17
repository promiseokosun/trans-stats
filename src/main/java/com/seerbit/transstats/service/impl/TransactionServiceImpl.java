package com.seerbit.transstats.service.impl;

import com.seerbit.transstats.constants.ResponseCodes;
import com.seerbit.transstats.constants.ResponseStatuses;
import com.seerbit.transstats.dao.TransactionDao;
import com.seerbit.transstats.dto.ApiResponse;
import com.seerbit.transstats.dto.TransactionRequest;
import com.seerbit.transstats.dto.TransactionStatisticsResponse;
import com.seerbit.transstats.exception.ApiException;
import com.seerbit.transstats.model.Transaction;
import com.seerbit.transstats.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDao transactionDao;

    @Override
    public ApiResponse createTransaction(TransactionRequest dto) {
        if(!isValidTransactionTime(dto.getTimestamp())) {
            return ApiResponse.getFailedResponse(ResponseCodes.BAD_INPUT_PARAM.getCode(), "Transaction date is older than 30s", HttpStatus.BAD_REQUEST);
        }
        Transaction transaction = transactionDao.save(new Transaction(null,
                BigDecimal.valueOf(Double.parseDouble(dto.getAmount())).setScale(2, RoundingMode.HALF_UP), LocalDateTime.parse(dto.getTimestamp())));
        return ApiResponse.getSuccessResponse(transaction, HttpStatus.CREATED);
    }

    @Override
    public Transaction getTransaction(String id) {
        return transactionDao.findById(id).orElseThrow(() -> new ApiException(ResponseCodes.RESOURCE_NOT_FOUND.getCode(),
                ResponseStatuses.failed.name(), "Transaction not found, transactionId: " + id, HttpStatus.BAD_REQUEST));
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDao.findAll();
    }

    @Override
    public void deleteAllTransactions() {
        transactionDao.deleteAll();
    }

    @Override
    public TransactionStatisticsResponse getStatistics(LocalDateTime time, int lastXseconds) {
        List<Transaction> transactions = transactionDao.findByTimestamp(time, lastXseconds);
        double sum = transactions.stream().mapToDouble(transaction -> transaction.getAmount().doubleValue()).sum();
        double min = transactions.stream().mapToDouble(transaction -> transaction.getAmount().doubleValue()).min().orElse(0.0D);
        double avg = transactions.stream().mapToDouble(transaction -> transaction.getAmount().doubleValue()).average().orElse(0.0D);
        double max = transactions.stream().mapToDouble(transaction -> transaction.getAmount().doubleValue()).max().orElse(0.0D);
        return new TransactionStatisticsResponse(sum+"", avg+"", min+"", max+"", transactions.size());
    }

    private boolean isValidTransactionTime(String timestamp) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime transTime = LocalDateTime.parse(timestamp);
        return ChronoUnit.SECONDS.between(transTime, currentTime) <= 30 && transTime.isAfter(currentTime);
    }

}
