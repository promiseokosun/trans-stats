package com.seerbit.transstats.dao.impl;

import com.seerbit.transstats.dao.TransactionDao;
import com.seerbit.transstats.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionDaoImplTest {

    @Autowired
    private TransactionDao transactionDao;
    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;

    @BeforeEach
    public void setup() {
        transaction1 = transactionDao.save(new Transaction(null, "30000", LocalDateTime.now()));
        transaction2 = transactionDao.save(new Transaction(null, "35000", LocalDateTime.now()));
        transaction3 = transactionDao.save(new Transaction(null, "40000", LocalDateTime.now()));
    }

    @Test
    void save() {
        assertNotNull(transactionDao.save(new Transaction(null, "20000", LocalDateTime.now())));
    }

    @Test
    void update() {
        assertNotEquals(transactionDao.save(new Transaction(transaction2.getId(), "10000", LocalDateTime.now())).getAmount(), "30000");
    }

    @Test
    void findById() {
        assertTrue(transactionDao.findById(transaction1.getId()).isPresent());
    }

    @Test
    void findAll() {
        assertTrue(transactionDao.findAll().size() > 0);
    }

    @Test
    void delete() {
        transactionDao.delete(transaction3.getId());
        assertTrue(transactionDao.findById(transaction3.getId()).isEmpty());
    }

    @Test
    void findByTimestamp() {
        assertTrue(transactionDao.findByTimestamp(LocalDateTime.now().plusSeconds(30), 30).size() > 0);
    }

    @Test
    void findByTimestampShouldReturnEmpty() {
        assertTrue(transactionDao.findByTimestamp(LocalDateTime.now().plusSeconds(31), 30).size() == 0);
    }

    @Disabled
    @Test
    void deleteAll() {
        transactionDao.deleteAll();
        assertTrue(transactionDao.findAll().size() == 0);
    }

}