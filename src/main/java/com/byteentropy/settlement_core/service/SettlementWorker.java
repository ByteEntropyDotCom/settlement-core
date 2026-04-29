package com.byteentropy.settlement_core.service;

import com.byteentropy.settlement_core.model.*;
import com.byteentropy.settlement_core.repository.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Service
public class SettlementWorker {

    private static final Logger logger = Logger.getLogger(SettlementWorker.class.getName());

    private final ClearingBatchRepository batchRepo;
    private final SettlementRepository settlementRepo;

    public SettlementWorker(ClearingBatchRepository batchRepo, SettlementRepository settlementRepo) {
        this.batchRepo = batchRepo;
        this.settlementRepo = settlementRepo;
    }

    @Scheduled(fixedDelay = 60000)
    public void processSettlements() {
        List<ClearingBatch> readyBatches = batchRepo.findByStatus(BatchStatus.COMPLETED);
        if (readyBatches.isEmpty()) return;

        for (ClearingBatch batch : readyBatches) {
            processSingleBatch(batch);
        }
    }

    @Transactional
    public void processSingleBatch(ClearingBatch batch) {
        try {
            BigDecimal netAmount = batch.getTotalAmount().subtract(batch.getTotalFees());

            if (netAmount.compareTo(BigDecimal.ZERO) <= 0) {
                logger.warning("Skipping Batch " + batch.getId() + " - Net amount is non-positive.");
                batch.setStatus(BatchStatus.FAILED);
                batchRepo.save(batch);
                return;
            }

            Settlement settlement = new Settlement();
            settlement.setClearingBatchId(batch.getId());
            settlement.setNetAmount(netAmount);
            settlement.setCurrency(batch.getCurrency());
            settlement.setSettledAt(LocalDateTime.now());

            settlementRepo.save(settlement);

            batch.setStatus(BatchStatus.SETTLED);
            batchRepo.save(batch);

            logger.info("Successfully settled Batch " + batch.getId() + " for " + netAmount + " " + batch.getCurrency());
        } catch (Exception e) {
            logger.severe("Critical error settling batch " + batch.getId() + ": " + e.getMessage());
        }
    }
}