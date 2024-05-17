package es.uca.dss.ParkControl.core.Subscription;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
public class SubscriptionServiceTest {
    private SubscriptionService subscriptionService;
    private SubscriptionRepository subscriptionRepository;
    private Subscription subscription;

    @Before
    public void SetUp(){
        subscriptionRepository = new InMemorySubscriptionRepository();
        subscriptionService = new SubscriptionService(subscriptionRepository);
        
        subscription = new Subscription();
        subscription.setId(UUID.randomUUID());
    }
    
    @Test
    public void testCreateSubscription(){
        subscriptionService.createSubscription(subscription);
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        assertEquals(1,subscriptions.size());

        Subscription retrievedSubscription = subscriptionRepository.findById(subscription.getId());
        assertEquals(retrievedSubscription,subscription);
    }
    
    @Test
    public void testGetSubscription(){
        subscriptionService.createSubscription(subscription);
        Subscription retrievedSubscription = subscriptionService.getSubscription(subscription.getId());
        assertEquals(retrievedSubscription,subscription);
    }
    
    @Test
    public void testGetAllSubscriptions(){
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        assertNotNull(subscriptions); //Entendemos null como no crear la lista
        assertEquals(0,subscriptions.size());
    }
    
    @Test
    public void testDeleteSubscription(){
        subscriptionService.createSubscription(subscription);
        subscriptionService.deleteSubscription(subscription.getId());
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        assertEquals(0,subscriptions.size());
    }

}
