package es.uca.dss.ParkControl.core.Transaction;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
public class InMemoryTransactionRepositoryTest {
    private InMemoryTransactionRepository inMemoryTransactionRepository;
    private Transaction transaction;
    @Before
    public void SetUp(){
        inMemoryTransactionRepository = new InMemoryTransactionRepository();
        transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
    }

    @Test
    public void testSave(){
        inMemoryTransactionRepository.save(transaction);
        List<Transaction> transactions = inMemoryTransactionRepository.findAll();
        assertEquals(1,transactions.size());
        Transaction retrievedTransaction = inMemoryTransactionRepository.findById(transaction.getId());
        assertEquals(retrievedTransaction,transaction);
    }

    @Test
    public void testFindById(){
        inMemoryTransactionRepository.save(transaction);
        Transaction retrievedTransaction = inMemoryTransactionRepository.findById(transaction.getId());
        assertEquals(retrievedTransaction,transaction);
    }

    @Test
    public void testFindAll(){
        List<Transaction> transactions = inMemoryTransactionRepository.findAll();
        assertNotNull(transactions);
        assertEquals(0,transactions.size());
    }

    @Test
    public void testDeleteById(){
        inMemoryTransactionRepository.save(transaction);
        inMemoryTransactionRepository.deleteById(transaction.getId());
        List<Transaction> transactions = inMemoryTransactionRepository.findAll();
        assertEquals(0,transactions.size());
    }
}
