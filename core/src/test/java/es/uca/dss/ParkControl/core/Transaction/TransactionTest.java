package es.uca.dss.ParkControl.core.Transaction;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
public class TransactionTest {

    private  Transaction transaction;

    @Before
    public void SetUp(){
        transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAmountOfPayment(15.15);
        transaction.setDateOfPayment(LocalDate.of(2024,3,4));
        transaction.setDone(false);
        transaction.setConceptID(UUID.randomUUID());
    }

    @Test
    public void testGettersAndSetters(){
        assertEquals(transaction.getId(),transaction.getId());
        assertEquals(15.15,transaction.getAmountOfPayment(),0.01);
        assertEquals(LocalDate.of(2024,3,4),transaction.getDateOfPayment());
        assertEquals(transaction.getConceptID(),transaction.getConceptID());
        assertEquals(false,transaction.isDone());

        transaction.setDone(true);
        assertEquals(true,transaction.isDone());
    }
}
