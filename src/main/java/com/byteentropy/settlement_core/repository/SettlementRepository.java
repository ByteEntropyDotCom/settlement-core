package com.byteentropy.settlement_core.repository;

import com.byteentropy.settlement_core.model.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
}