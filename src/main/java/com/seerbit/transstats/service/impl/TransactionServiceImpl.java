package com.seerbit.transstats.service.impl;

import com.seerbit.transstats.constants.ResponseCodes;
import com.seerbit.transstats.constants.ResponseStatuses;
import com.seerbit.transstats.dao.TransactionDao;
import com.seerbit.transstats.dto.TransactionRequest;
import com.seerbit.transstats.dto.TransactionStatisticsResponse;
import com.seerbit.transstats.exception.ApiException;
import com.seerbit.transstats.model.Transaction;
import com.seerbit.transstats.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDao transactionDao;

    @Override
    public Transaction createTransaction(TransactionRequest dto) {
        return transactionDao.save(new Transaction(null, dto.getAmount(), LocalDateTime.parse(dto.getTimeStamp())));
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
        double sum = transactions.stream().mapToDouble(transaction -> Double.parseDouble(transaction.getAmount())).sum();
        double min = transactions.stream().mapToDouble(transaction -> Double.parseDouble(transaction.getAmount())).min().getAsDouble();
        double avg = transactions.stream().mapToDouble(transaction -> Double.parseDouble(transaction.getAmount())).average().orElse(0.0D);
        double max = transactions.stream().mapToDouble(transaction -> Double.parseDouble(transaction.getAmount())).max().getAsDouble();
        return new TransactionStatisticsResponse(sum+"", avg+"", min+"", max+"", transactions.size());
    }

}
