package com.byteentropy.settlement_core.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "settlements")
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long clearingBatchId;
    
    @Column(precision = 19, scale = 4)
    private BigDecimal netAmount;
    
    private String currency;
    private LocalDateTime settledAt;

    public Settlement() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClearingBatchId() { return clearingBatchId; }
    public void setClearingBatchId(Long clearingBatchId) { this.clearingBatchId = clearingBatchId; }

    public BigDecimal getNetAmount() { return netAmount; }
    public void setNetAmount(BigDecimal netAmount) { this.netAmount = netAmount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public LocalDateTime getSettledAt() { return settledAt; }
    public void setSettledAt(LocalDateTime settledAt) { this.settledAt = settledAt; }
}