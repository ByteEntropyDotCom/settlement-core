package com.byteentropy.settlement_core.repository;

import com.byteentropy.settlement_core.model.BatchStatus;
import com.byteentropy.settlement_core.model.ClearingBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClearingBatchRepository extends JpaRepository<ClearingBatch, Long> {
    List<ClearingBatch> findByStatus(BatchStatus status);
}