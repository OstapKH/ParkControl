package es.uca.dss.ParkControl.api_http.TransactionEntity;

import es.uca.dss.ParkControl.core.Transaction.Transaction;
import es.uca.dss.ParkControl.core.Transaction.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SpringTransactionAdapterRepository implements TransactionRepository {
    private final SpringTransactionJpaRepository springTransactionJpaRepository;

    public SpringTransactionAdapterRepository(SpringTransactionJpaRepository springTransactionJpaRepository) {
        this.springTransactionJpaRepository = springTransactionJpaRepository;
    }
    @Override
    public void save(Transaction transaction) {
        springTransactionJpaRepository.save(transaction);
    }

    @Override
    public Transaction findById(UUID id) {
        return springTransactionJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Transaction> findAll() {
        return springTransactionJpaRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        springTransactionJpaRepository.deleteById(id);
    }
}
