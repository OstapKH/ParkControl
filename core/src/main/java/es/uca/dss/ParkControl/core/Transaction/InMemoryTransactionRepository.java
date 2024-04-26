package es.uca.dss.ParkControl.core.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryTransactionRepository implements TransactionRepository {
    private List<Transaction> transactions = new ArrayList<>();

    @Override
    public void save(Transaction transaction) {
        UUID id = transaction.getId();
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId().equals(id)) {
                transactions.set(i, transaction);
                return;
            }
        }
        transactions.add(transaction);
    }

    @Override
    public Transaction findById(UUID id) {
        for (Transaction transaction : transactions) {
            if (transaction.getId().equals(id)) {
                return transaction;
            }
        }
        return null;
    }

    @Override
    public List<Transaction> findAll() {
        return transactions;
    }

    @Override
    public void deleteById(UUID id) {
        transactions.removeIf(transaction -> transaction.getId().equals(id));
    }
}
