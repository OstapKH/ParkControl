package es.uca.dss.ParkControl.core.Subscription;

import java.util.*;

public class InMemorySubscriptionTypeRepository implements SubscriptionTypeRepository{
    public Set<SubscriptionType> subscriptionTypes = new HashSet<>();

    @Override
    public void save(SubscriptionType subscriptionType) {
        // TODO What if I want to compare them by name? Is my implementation OK?
        subscriptionTypes.add(subscriptionType);
    }

    @Override
    public SubscriptionType findById(UUID id) {
        for (SubscriptionType subscriptionType: subscriptionTypes){
            if (subscriptionType.getId().equals(id)){
                return subscriptionType;
            }
        }
        return null;
    }

    @Override
    public SubscriptionType findByName(String name) {
        for(SubscriptionType subscriptionType: subscriptionTypes){
            if (subscriptionType.getName().equals(name)){
                return subscriptionType;
            }
        }
        return null;
    }

    @Override
    public List<SubscriptionType> findAll() {
        return subscriptionTypes.stream().toList();
    }

    @Override
    public void deleteById(UUID id) {
            subscriptionTypes.removeIf(subscriptionType -> subscriptionType.getId().equals(id));
    }
}
