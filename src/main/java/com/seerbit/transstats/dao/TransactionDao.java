package com.seerbit.transstats.dao;

import com.seerbit.transstats.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionDao {
    Transaction save(Transaction transaction);

    Optional<Transaction> findById(String id);

    List<Transaction> findAll();

    void delete(String id);

    List<Transaction> findByTimestamp(LocalDateTime timestamp, int lastXseconds);

    void deleteAll();
}
