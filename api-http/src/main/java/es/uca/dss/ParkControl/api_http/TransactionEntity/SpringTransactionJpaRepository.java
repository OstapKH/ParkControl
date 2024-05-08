package es.uca.dss.ParkControl.api_http.TransactionEntity;

import es.uca.dss.ParkControl.core.Transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface SpringTransactionJpaRepository extends JpaRepository<Transaction, UUID> {
}
