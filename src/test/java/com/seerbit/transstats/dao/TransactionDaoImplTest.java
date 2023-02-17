package com.seerbit.transstats.dao;

import com.seerbit.transstats.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionDaoImplTest {

    @Autowired
    private TransactionDao transactionDao;
    private Transaction transaction;
    private String transactionId1 = "1";
    private String transactionId2 = "2";
    private String transactionId3 = "3";

    @BeforeEach
    public void setup() {
        transaction = new Transaction(transactionId1, "20000", LocalDateTime.now());
        transactionDao.save(new Transaction(transactionId2, "30000", LocalDateTime.now()));
        transactionDao.save(new Transaction(transactionId3, "40000", LocalDateTime.now()));
    }

    @Test
    void save() {
        assertNotNull(transactionDao.save(transaction));
    }

    @Test
    void update() {
        assertNotEquals(transactionDao.update(transactionId1, new Transaction(transactionId1, "10000", LocalDateTime.now())).getAmount(), transaction.getAmount());
    }

    @Test
    void findById() {
        assertNotNull(transactionDao.findById(transactionId2));
    }

    @Test
    void findAll() {
        assertTrue(transactionDao.findAll().size() > 0);
    }

    @Test
    void delete() {
        transactionDao.delete(transactionId3);
        assertNull(transactionDao.findById(transactionId3));
    }

    @Test
    void findByTimestamp() {
        assertTrue(transactionDao.findByTimestamp(LocalDateTime.now().plusSeconds(30), 30).size() > 0);
    }

    @Test
    void findByTimestampShouldReturnEmpty() {
        assertTrue(transactionDao.findByTimestamp(LocalDateTime.now().plusSeconds(31), 30).size() == 0);
    }
}