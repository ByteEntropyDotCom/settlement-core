package com.byteentropy.settlement_core.service;

import com.byteentropy.settlement_core.model.*;
import com.byteentropy.settlement_core.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SettlementWorkerTest {

    @Autowired
    private SettlementWorker settlementWorker;

    @Autowired
    private ClearingBatchRepository batchRepo;

    @Autowired
    private SettlementRepository settlementRepo;

    @BeforeEach
    void clean() {
        settlementRepo.deleteAll();
        batchRepo.deleteAll();
    }

    @Test
    @DisplayName("Happy Path: Correct calculation and status update")
    void testSettlementCalculation() {
        ClearingBatch batch = createBatch("1000.00", "15.00", BatchStatus.COMPLETED);
        settlementWorker.processSettlements();

        Settlement result = settlementRepo.findAll().get(0);
        assertEquals(0, new BigDecimal("985.00").compareTo(result.getNetAmount()));
        assertEquals("USD", result.getCurrency());
        assertEquals(BatchStatus.SETTLED, batchRepo.findById(batch.getId()).get().getStatus());
    }

    @Test
    @DisplayName("Guard Case: Skip negative net amounts")
    void testNegativeNetProtection() {
        createBatch("10.00", "15.00", BatchStatus.COMPLETED);
        settlementWorker.processSettlements();

        assertEquals(0, settlementRepo.count());
        assertEquals(BatchStatus.FAILED, batchRepo.findAll().get(0).getStatus());
    }

    private ClearingBatch createBatch(String amount, String fees, BatchStatus status) {
        ClearingBatch batch = new ClearingBatch();
        batch.setTotalAmount(new BigDecimal(amount));
        batch.setTotalFees(new BigDecimal(fees));
        batch.setCurrency("USD");
        batch.setStatus(status);
        return batchRepo.save(batch);
    }
}