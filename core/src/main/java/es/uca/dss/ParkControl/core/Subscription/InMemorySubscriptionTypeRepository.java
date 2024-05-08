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
    public Optional<SubscriptionType> findById(UUID id) {
        for (SubscriptionType subscriptionType: subscriptionTypes){
            if (subscriptionType.getId().equals(id)){
                return Optional.of(subscriptionType);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<SubscriptionType> findByName(String name) {
        for(SubscriptionType subscriptionType: subscriptionTypes){
            if (subscriptionType.getName().equals(name)){
                return Optional.of(subscriptionType);
            }
        }
        return Optional.empty();
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
