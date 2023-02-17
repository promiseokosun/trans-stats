package com.seerbit.transstats.service.impl;

import com.seerbit.transstats.dao.TransactionDao;
import com.seerbit.transstats.dto.TransactionRequest;
import com.seerbit.transstats.dto.TransactionStatisticsResponse;
import com.seerbit.transstats.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
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
    private TransactionRequest transactionRequestMock2;
    private TransactionRequest transactionRequestMock3;
    private Transaction transaction;
    private Transaction transaction2;
    private Transaction transaction3;
    private BigDecimal min = BigDecimal.valueOf(2000.0);
    private BigDecimal max = BigDecimal.valueOf(4000.0);

    @BeforeEach
    public void setup() {
        transactionRequestMock = new TransactionRequest("3000", LocalDateTime.now() +"");
        transactionRequestMock2 = new TransactionRequest(min.toString(), LocalDateTime.now() +"");
        transactionRequestMock3 = new TransactionRequest(max.toString(), LocalDateTime.now() +"");
        transaction = new Transaction(null, BigDecimal.valueOf(Double.parseDouble(transactionRequestMock.getAmount())), LocalDateTime.parse(transactionRequestMock.getTimestamp()));
        transaction2 = new Transaction(null, BigDecimal.valueOf(Double.parseDouble(transactionRequestMock2.getAmount())), LocalDateTime.parse(transactionRequestMock2.getTimestamp()));
        transaction3 = new Transaction(null, BigDecimal.valueOf(Double.parseDouble(transactionRequestMock3.getAmount())), LocalDateTime.parse(transactionRequestMock3.getTimestamp()));
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
        when(transactionDao.findByTimestamp(time, lastXseconds)).thenReturn(Arrays.asList(transaction, transaction2, transaction3));
        TransactionStatisticsResponse statistics = transactionService.getStatistics(time, lastXseconds);
        assertTrue(statistics.getMin().equals(min+""));
        assertTrue(statistics.getMax().equals(max+""));
        assertTrue(statistics.getSum().equals("9000.0"));
        assertTrue(statistics.getAvg().equals("3000.0"));
    }
}