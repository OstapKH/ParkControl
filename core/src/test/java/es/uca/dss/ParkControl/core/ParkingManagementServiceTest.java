package es.uca.dss.ParkControl.core;

import es.uca.dss.ParkControl.core.Parking.InMemoryParkingRepository;
import es.uca.dss.ParkControl.core.Parking.Parking;
import es.uca.dss.ParkControl.core.ParkingManagement.ParkingManagementService;
import es.uca.dss.ParkControl.core.Plan.InMemoryPlanRepository;
import es.uca.dss.ParkControl.core.Plan.Plan;
import es.uca.dss.ParkControl.core.Plan.PlanType;
import es.uca.dss.ParkControl.core.Record.InMemoryRecordRepository;
import es.uca.dss.ParkControl.core.Subscription.InMemorySubscriptionRepository;
import es.uca.dss.ParkControl.core.Subscription.InMemorySubscriptionTypeRepository;
import es.uca.dss.ParkControl.core.Subscription.Subscription;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionType;
import es.uca.dss.ParkControl.core.Ticket.InMemoryTicketRepository;
import es.uca.dss.ParkControl.core.Ticket.Ticket;
import es.uca.dss.ParkControl.core.Transaction.InMemoryTransactionRepository;
import es.uca.dss.ParkControl.core.Vehicle.InMemoryVehicleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class ParkingManagementServiceTest {
    private ParkingManagementService service;
    @Before
    public void setUp() {
        InMemorySubscriptionRepository inMemorySubscriptionRepository = new InMemorySubscriptionRepository();
        InMemorySubscriptionTypeRepository inMemorySubscriptionTypeRepository = new InMemorySubscriptionTypeRepository();
        InMemoryPlanRepository inMemoryPlanRepository = new InMemoryPlanRepository();
        InMemoryTicketRepository inMemoryTicketRepository = new InMemoryTicketRepository();
        InMemoryParkingRepository inMemoryParkingRepository = new InMemoryParkingRepository();
        InMemoryTransactionRepository inMemoryTransactionRepository = new InMemoryTransactionRepository();
        InMemoryVehicleRepository inMemoryVehicleRepository = new InMemoryVehicleRepository();
        InMemoryRecordRepository inMemoryRecordRepository = new InMemoryRecordRepository();
        service = new ParkingManagementService(inMemoryParkingRepository, inMemoryPlanRepository, inMemorySubscriptionRepository, inMemoryTicketRepository, inMemoryTransactionRepository, inMemoryVehicleRepository, inMemoryRecordRepository, inMemorySubscriptionTypeRepository);
    }

    @Test
    public void testCreateParking() {
        // Arrange
        String name = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";

        // Act
        UUID parkingId = service.createParking(name, maxNumberOfSpaces, zipCode);

        // Assert
        Parking parking = service.getParkingById(parkingId);
        Assert.assertNotNull(parking);
        Assert.assertEquals(name, parking.getName());
        Assert.assertEquals(maxNumberOfSpaces, parking.getMaxNumberOfSpaces());
        Assert.assertEquals(zipCode, parking.getZipCode());
    }

    @Test
    public void testChangeParkingDetails() {
        // Arrange
        String originalName = "Original Parking";
        int originalMaxNumberOfSpaces = 100;
        String originalZipCode = "12345";
        UUID parkingId = service.createParking(originalName, originalMaxNumberOfSpaces, originalZipCode);

        String newName = "New Parking";
        int newMaxNumberOfSpaces = 200;
        String newZipCode = "67890";

        // Act
        service.changeParkingDetails(parkingId, newName, newMaxNumberOfSpaces, newZipCode);

        // Assert
        Parking parking = service.getParkingById(parkingId);
        Assert.assertNotNull(parking);
        Assert.assertEquals(newName, parking.getName());
        Assert.assertEquals(newMaxNumberOfSpaces, parking.getMaxNumberOfSpaces());
        Assert.assertEquals(newZipCode, parking.getZipCode());
    }

    @Test
    public void testAddVehicleToParking() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = service.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";

        // Act
        Optional<Ticket> ticketOptional = service.addVehicleToParking(parkingId, registrationNumber);

        // Assert
        Assert.assertTrue(ticketOptional.isPresent());
        Ticket ticket = ticketOptional.get();
        Assert.assertEquals(registrationNumber, ticket.getVehicle().getRegistrationNumber());
        Assert.assertEquals(parkingId, ticket.getParking().getId());
    }

    @Test
    public void testAddVehicleToParkingWithoutRegistrationNumber() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = service.createParking(parkingName, maxNumberOfSpaces, zipCode);

        // Act
        Optional<Ticket> ticketOptional = service.addVehicleToParking(parkingId);

        // Assert
        Assert.assertTrue(ticketOptional.isPresent());
        Ticket ticket = ticketOptional.get();
        Assert.assertNotNull(ticket.getVehicle());
        Assert.assertEquals(parkingId, ticket.getParking().getId());
    }

    @Test
    public void testVehicleExit() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = service.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = service.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();
        service.paymentOfTicketByCard(ticket.getId());

        // Act
        boolean isExitPermitted = service.vehicleExit(parkingId, registrationNumber);

        // Assert
        Assert.assertTrue(isExitPermitted);
    }

    @Test
    public void testVehicleExitWithTicket() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = service.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = service.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();
        service.paymentOfTicketByCard(ticket.getId());

        // Act
        boolean isExitPermitted = service.vehicleExit(parkingId, ticket);

        // Assert
        Assert.assertTrue(isExitPermitted);
    }



    @Test
    public void testGetTicketPrice1Min() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = service.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = service.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();
        ticket.setDateOfIssue(LocalDateTime.now().minusMinutes(1));

        // Act
        double ticketPrice = service.getTicketPrice(ticket.getId());

        // Assert
        double expectedPrice = 1 * 0.2;
        Assert.assertEquals(expectedPrice, ticketPrice, 0.01);  // The third parameter is the delta, which allows for a small error due to floating point precision.
    }

    @Test
    public void testGetTicketPrice1Day() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = service.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = service.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();
        Plan dayPlan = new Plan();
        dayPlan.setPlanType(PlanType.DAYS);
        dayPlan.setPlanName("Day Plan");
        dayPlan.setPrice(5.0);
        service.changeTicketPlan(ticket.getId(), dayPlan);
        ticket.setDateOfIssue(LocalDateTime.now().minusDays(1));

        // Act
        double ticketPrice = service.getTicketPrice(ticket.getId());

        // Assert
        double expectedPrice = 1 * 5.0;
        Assert.assertEquals(expectedPrice, ticketPrice, 0.01);  // The third parameter is the delta, which allows for a small error due to floating point precision.
    }


    @Test
    public void testChangeTicketPlan() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = service.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = service.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();

        Plan newPlan = new Plan();
        newPlan.setPlanName("New Plan");
        newPlan.setPlanType(PlanType.DAYS);
        newPlan.setId(UUID.randomUUID());
        newPlan.setPrice(5.0);

        // Act
        service.changeTicketPlan(ticket.getId(), newPlan);

        // Assert
        Assert.assertEquals(newPlan.getPlanName(), ticket.getPlan().getPlanName());
        Assert.assertEquals(newPlan.getPlanType(), ticket.getPlan().getPlanType());
        Assert.assertEquals(newPlan.getPrice(), ticket.getPlan().getPrice(), 0.01);
    }

    @Test
    public void testPaymentOfTicketByCard() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = service.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = service.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();

        Assert.assertNull(ticket.getDateOfPayment());

        // Act
        service.paymentOfTicketByCard(ticket.getId());

        // Assert
        Assert.assertNotNull(ticket.getDateOfPayment());
    }

    @Test
    public void testPaymentOfTicketByCash() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = service.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = service.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();
        double amountPaid = 10.0; // This should be more than the ticket price to test the change return

        // Act
        double change = service.paymentOfTicketByCash(ticket.getId(), amountPaid);

        // Assert
        Assert.assertNotNull(ticket.getDateOfPayment()); // Check if payment date is set
        Assert.assertTrue(change >= 0); // Check if the change returned is not negative
    }


    @Test
    public void testSubscribeVehicle() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = service.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = service.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();

        SubscriptionType subscriptionType = new SubscriptionType();
        subscriptionType.setId(UUID.randomUUID());
        subscriptionType.setName("Monthly");
        subscriptionType.setPrice(50.0);

        // Act
        Subscription subscription = service.subscribeVehicle(registrationNumber, subscriptionType);

        // Assert
        Assert.assertNotNull(subscription); // Check if subscription is not null
        Assert.assertEquals(subscriptionType, subscription.getSubscriptionType()); // Check if the subscription type is correct
        Assert.assertEquals(registrationNumber, subscription.getVehicle().getRegistrationNumber()); // Check if the vehicle registration number is correct
    }

    @Test
    public void testCreateSubscriptionType() {
        // Arrange
        String subscriptionTypeName = "Yearly";
        double subscriptionTypePrice = 500.0;

        // Act
        UUID subscriptionId = service.createSubscriptionType(subscriptionTypeName, subscriptionTypePrice);

        // Assert
        Optional<SubscriptionType> subscriptionType = service.getSubscriptionTypeById(subscriptionId);
        Assert.assertNotNull(subscriptionType); // Check if subscription type is not null
        Assert.assertEquals(subscriptionTypeName, subscriptionType.get().getName()); // Check if the subscription type name is correct
        Assert.assertEquals(subscriptionTypePrice, subscriptionType.get().getPrice(), 0.01); // Check if the subscription type price is correct
    }

    @Test
    public void testChangeSubscriptionTypePrice() {
        // Arrange
        String subscriptionTypeName = "Monthly";
        double originalPrice = 50.0;
        double newPrice = 60.0;

        UUID subscriptionTypeId = service.createSubscriptionType(subscriptionTypeName, originalPrice);
        Optional<SubscriptionType> originalSubscriptionType = service.getSubscriptionTypeById(subscriptionTypeId);

        // Act
        service.changeSubscriptionTypePrice(subscriptionTypeName, newPrice);

        // Assert
        SubscriptionType updatedSubscriptionType = service.getSubscriptionTypeById(subscriptionTypeId).get();
        Assert.assertNotNull(updatedSubscriptionType); // Check if subscription type is not null
        Assert.assertEquals(subscriptionTypeName, updatedSubscriptionType.getName()); // Check if the subscription type name is correct
        Assert.assertEquals(newPrice, updatedSubscriptionType.getPrice(), 0.01); // Check if the subscription type price is updated correctly
    }

    @Test
    public void testPaymentOfSubscriptionByCard() {
        // Arrange
        String subscriptionTypeName = "Monthly";
        double subscriptionTypePrice = 50.0;
        UUID subscriptionTypeId = service.createSubscriptionType(subscriptionTypeName, subscriptionTypePrice);
        Optional<SubscriptionType> subscriptionType = service.getSubscriptionTypeById(subscriptionTypeId);
        String registrationNumber = "ABC123";
        Subscription subscription = service.subscribeVehicle(registrationNumber, subscriptionType.get());

        // Act
        service.paymentOfSubscriptionByCard(subscription.getId());

        // Assert
        Subscription updatedSubscription = service.getSubscriptionById(subscription.getId());
        Assert.assertNotNull(updatedSubscription.getDateOfPurchase()); // Check if purchase date is set
    }

    @Test
    public void testMultipleCarsEnterParking() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = service.createParking(parkingName, maxNumberOfSpaces, zipCode);

        // Act
        // Add 5 vehicles to parking
        for (int i = 0; i < 5; i++) {
            String registrationNumber = "ABC" + (i + 123); // Unique registration number for each vehicle
            Optional<Ticket> ticketOptional = service.addVehicleToParking(parkingId, registrationNumber);
            // Check if corresponding were created and not null
            Assert.assertTrue(ticketOptional.isPresent());
        }

        Parking parking = service.getParkingById(parkingId);
        // Assert
        // Check if after entering of 5 vehicles, amount of available spaces was decreased correctly
        Assert.assertEquals(95, service.getParkingById(parkingId).getCurrentAvailableNumberOfSpaces()); // Check if the number of available spaces is correct
    }
}

