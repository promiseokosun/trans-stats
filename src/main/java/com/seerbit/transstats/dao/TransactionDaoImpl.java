package com.seerbit.transstats.dao;

import com.seerbit.transstats.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TransactionDaoImpl implements TransactionDao{
    private Map<String, Transaction> transactionMap = new HashMap<>();

    @Override
    public Transaction save(Transaction transaction) {
        transactionMap.put(transaction.getId(), transaction);
        return findById(transaction.getId());
    }

    @Override
    public Transaction update(String id, Transaction transaction) {
        transactionMap.put(id, transaction);
        return findById(transaction.getId());
    }

    @Override
    public Transaction findById(String id) {
        return transactionMap.get(id);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionMap.values().stream().toList();
    }

    @Override
    public void delete(String id) {
        transactionMap.remove(id);
    }

    @Override
    public List<Transaction> findByTimestamp(LocalDateTime timestamp, int lastXseconds) {
        return transactionMap.values().stream()
                .filter(transaction -> ChronoUnit.SECONDS.between(transaction.getTimestamp(), timestamp) <= lastXseconds).toList();
    }
}
