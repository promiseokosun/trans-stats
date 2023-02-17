package com.seerbit.transstats.dao;

import com.seerbit.transstats.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionDao {
    Transaction save(Transaction transaction);

    Transaction update(String id, Transaction transaction);

    Transaction findById(String id);

    List<Transaction> findAll();

    void delete(String id);

    List<Transaction> findByTimestamp(LocalDateTime timestamp, int lastXseconds);
}
