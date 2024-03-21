package es.uca.dss.ParkControl.core.Transaction;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class TransactionServiceTest {
    private TransactionService transactionService;
    private TransactionRepository transactionRepository;
    private Transaction transaction;

    @Before
    public void SetUp(){
        transactionRepository = new InMemoryTransactionRepository();
        transactionService = new TransactionService(transactionRepository);

        transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
    }

    @Test
    public void testCreateTransaction(){
        transactionService.createTransaction(transaction);
        List<Transaction> transactions = transactionRepository.findAll();
        assertEquals(1,transactions.size());

        Transaction retrievedTransaction = transactionRepository.findById(transaction.getId());
        assertEquals(retrievedTransaction,transaction);
    }

    @Test
    public void testGetTransaction(){
        transactionService.createTransaction(transaction);
        Transaction retrievedTransaction = transactionService.getTransaction(transaction.getId());
        assertEquals(retrievedTransaction,transaction);
    }

    @Test
    public void testGetAlltransactions(){
        List<Transaction> transactions = transactionService.getAllTransactions();
        assertNotNull(transactions); //Entendemos null como no crear la lista
        assertEquals(0,transactions.size());
    }

    @Test
    public void testDeletetransaction(){
        transactionService.createTransaction(transaction);
        transactionService.deleteTransaction(transaction.getId());
        List<Transaction> transactions = transactionService.getAllTransactions();
        assertEquals(0,transactions.size());
    }
}
