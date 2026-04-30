# settlement-core

settlement-core is a backend service responsible for processing completed clearing batches. It calculates the net amount (Total Amount - Fees), ensures financial integrity by blocking negative settlements, and persists the final settlement records.

## Key Features

1. Automated Settlement: A scheduled worker (SettlementWorker) polls for COMPLETED batches every 60 seconds.
2. Financial Guardrails: Automatically fails batches where fees exceed the total amount to prevent negative payouts.
3. Data Integrity: Uses Jakarta Persistence (JPA) with @Transactional boundaries and @Version for optimistic locking.
4. Containerized: Ready for deployment via Docker and Docker Compose.

## 🛠 Tech Stack
1. Runtime: Java 21 (Temurin)
2. Framework: Spring Boot 4.0.6
3. Database: H2 (In-Memory for development/testing)
4. Build Tool: Maven
4. Containerization: Docker & Docker Compose

## 📁 Project Structure

```Plaintext
src/main/java/com/byteentropy/settlement_core/
├── model/           # JPA Entities (ClearingBatch, Settlement) and Enums
├── repository/      # Spring Data JPA Repositories
├── service/         # Business logic and Scheduled Workers
└── SettlementCoreApplication.java  # Main entry point
```

## 📝 License
This project is licensed under the MIT License.
