package es.uca.dss.ParkControl.core.Subscription;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
public class InMemorySubscriptionRepositoryTest {
    private InMemorySubscriptionRepository inMemorySubscriptionRepository;
    private Subscription subscription;
    @Before
    public void SetUp(){
        inMemorySubscriptionRepository = new InMemorySubscriptionRepository();
        subscription = new Subscription();
        subscription.setId(UUID.randomUUID());
    }

    @Test
    public void testSave(){
        inMemorySubscriptionRepository.save(subscription);
        List<Subscription> subscriptions = inMemorySubscriptionRepository.findAll();
        assertEquals(1,subscriptions.size());

        Subscription retrievedSubscription = inMemorySubscriptionRepository.findById(subscription.getId());
        assertEquals(retrievedSubscription,subscription);
    }

    @Test
    public void testFindById(){
        inMemorySubscriptionRepository.save(subscription);
        Subscription retrievedSubscription = inMemorySubscriptionRepository.findById(subscription.getId());
        assertEquals(retrievedSubscription,subscription);
    }

    @Test
    public void testFindAll(){
        List<Subscription> subscriptions = inMemorySubscriptionRepository.findAll();
        assertNotNull(subscriptions);
        assertEquals(0,subscriptions.size());
    }

    @Test
    public void testDeleteById(){
        inMemorySubscriptionRepository.save(subscription);
        inMemorySubscriptionRepository.deleteById(subscription.getId());
        List<Subscription> subscriptions = inMemorySubscriptionRepository.findAll();
        assertEquals(0,subscriptions.size());
    }
}
