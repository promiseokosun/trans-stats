package com.seerbit.transstats.service;

import com.seerbit.transstats.dto.ApiResponse;
import com.seerbit.transstats.dto.TransactionRequest;
import com.seerbit.transstats.dto.TransactionStatisticsResponse;
import com.seerbit.transstats.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    ApiResponse createTransaction(TransactionRequest dto);

    Transaction getTransaction(String id);

    List<Transaction> getAllTransactions();

    void deleteAllTransactions();

    TransactionStatisticsResponse getStatistics(LocalDateTime time, int lastXseconds);
}
