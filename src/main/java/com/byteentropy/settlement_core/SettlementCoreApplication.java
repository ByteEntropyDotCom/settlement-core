package com.byteentropy.settlement_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SettlementCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(SettlementCoreApplication.class, args);
    }
}