package com.seerbit.transstats.service.impl;

import com.seerbit.transstats.dao.TransactionDao;
import com.seerbit.transstats.dto.TransactionRequest;
import com.seerbit.transstats.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionDao transactionDao;
    private TransactionRequest transactionRequestMock;
    private Transaction transaction;

    @BeforeEach
    public void setup() {
        transactionRequestMock = new TransactionRequest("60000", LocalDateTime.now() +"");
        transaction = new Transaction(null, transactionRequestMock.getAmount(), LocalDateTime.parse(transactionRequestMock.getTimeStamp()));
    }

    @Test
    void createTransaction() {
        when(transactionDao.save(transaction)).thenReturn(transaction);
        assertNotNull(transactionService.createTransaction(transactionRequestMock));
    }

    @Test
    void getTransaction() {
        String transactionId = "11111";
        when(transactionDao.findById(transactionId)).thenReturn(Optional.ofNullable(transaction));
        assertNotNull(transactionService.getTransaction(transactionId));
    }

    @Test
    void getAllTransactions() {
        when(transactionDao.findAll()).thenReturn(Arrays.asList(transaction));
        assertTrue(transactionService.getAllTransactions().size() > 0);
    }

    @Test
    void deleteAllTransactions() {
        assertTrue(transactionService.getAllTransactions().size() > 0);
    }

    @Test
    void getStatistics() {
        LocalDateTime time = LocalDateTime.now();
        int lastXseconds = 30;
        when(transactionDao.findByTimestamp(time, lastXseconds)).thenReturn(Arrays.asList(transaction));
        assertNotNull(transactionService.getStatistics(time, lastXseconds));
    }
}