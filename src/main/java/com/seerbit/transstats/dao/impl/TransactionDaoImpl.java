package com.seerbit.transstats.dao.impl;

import com.seerbit.transstats.dao.TransactionDao;
import com.seerbit.transstats.model.Transaction;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TransactionDaoImpl implements TransactionDao {
    private Map<String, Transaction> transactionMap = new HashMap<>();
    private static int id = 1000;

    @Override
    public Transaction save(Transaction transaction) {
        String transactionId = Strings.isBlank(transaction.getId()) ? ++id + "" : transaction.getId();
        transaction.setId(transactionId);
        transactionMap.put(transactionId, transaction);
        return findById(transactionId).get();
    }

    @Override
    public Optional<Transaction> findById(String id) {
        return Optional.ofNullable(transactionMap.get(id));
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

    @Override
    public void deleteAll() {
        transactionMap = new HashMap<>();
    }
}
