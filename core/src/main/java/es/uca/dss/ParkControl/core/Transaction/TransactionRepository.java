package es.uca.dss.ParkControl.core.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {
    void save(Transaction transaction);
    Transaction findById(UUID id);
    List<Transaction> findAll();
    void deleteById(UUID id);
}
