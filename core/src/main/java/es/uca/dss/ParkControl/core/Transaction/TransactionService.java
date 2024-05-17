package es.uca.dss.ParkControl.core.Transaction;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void createTransaction(Transaction transaction) {
        repository.save(transaction);
    }

    public Transaction getTransaction(UUID id) {
        return repository.findById(id);
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public void deleteTransaction(UUID id) {
        repository.deleteById(id);
    }
}
