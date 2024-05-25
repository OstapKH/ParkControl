package es.uca.dss.ParkControl.core;

import es.uca.dss.ParkControl.core.Parking.InMemoryParkingRepository;
import es.uca.dss.ParkControl.core.Parking.Parking;
import es.uca.dss.ParkControl.core.ParkingManagement.*;
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
    private ParkingManagementService parkingManagementService;

    private ParkingEntranceAndExitManagementService parkingEntranceAndExitManagementService;

    private ParkingPaymentManagementService parkingPaymentManagementService;

    private ParkingStatisticsManagementService parkingStatisticsManagementService;

    private ParkingSubscriptionManagementService parkingSubscriptionManagementService;

    private ParkingTicketManagementService parkingTicketManagementService;

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

        parkingManagementService = new ParkingManagementService(inMemoryParkingRepository);
        parkingEntranceAndExitManagementService = new ParkingEntranceAndExitManagementService(inMemoryParkingRepository, inMemoryVehicleRepository, inMemoryTicketRepository, inMemoryRecordRepository, inMemorySubscriptionRepository, inMemoryPlanRepository);
        parkingPaymentManagementService = new ParkingPaymentManagementService(inMemorySubscriptionRepository, inMemoryTransactionRepository, inMemoryTicketRepository);
        parkingStatisticsManagementService = new ParkingStatisticsManagementService(inMemoryRecordRepository);
        parkingSubscriptionManagementService = new ParkingSubscriptionManagementService(inMemorySubscriptionRepository, inMemoryVehicleRepository, inMemorySubscriptionTypeRepository);
        parkingTicketManagementService = new ParkingTicketManagementService(inMemoryTicketRepository, inMemoryPlanRepository);

    }

    @Test
    public void testCreateParking() {
        // Arrange
        String name = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";

        // Act
        UUID parkingId = parkingManagementService.createParking(name, maxNumberOfSpaces, zipCode);

        // Assert

        Optional<Parking>
                parking = parkingManagementService.getParkingById(parkingId);
        if (parking.isPresent()) {
            Assert.assertNotNull(parking.get());
            Assert.assertEquals(name, parking.get().getName());
            Assert.assertEquals(maxNumberOfSpaces, parking.get().getMaxNumberOfSpaces());
            Assert.assertEquals(zipCode, parking.get().getZipCode());
        } else {
            Assert.fail("Parking not found");
        }
    }

    @Test
    public void testChangeParkingDetails() {
        // Arrange
        String originalName = "Original Parking";
        int originalMaxNumberOfSpaces = 100;
        String originalZipCode = "12345";
        UUID parkingId = parkingManagementService.createParking(originalName, originalMaxNumberOfSpaces, originalZipCode);

        String newName = "New Parking";
        int newMaxNumberOfSpaces = 200;
        String newZipCode = "67890";

        // Act
        parkingManagementService.changeParkingDetails(parkingId, newName, newMaxNumberOfSpaces, newZipCode);

        // Assert
        Optional<Parking> parking = parkingManagementService.getParkingById(parkingId);
        if (parking.isPresent()) {
            Assert.assertNotNull(parking.get());
            Assert.assertEquals(newName, parking.get().getName());
            Assert.assertEquals(newMaxNumberOfSpaces, parking.get().getMaxNumberOfSpaces());
            Assert.assertEquals(newZipCode, parking.get().getZipCode());
        } else
            Assert.fail("Parking not found");

    }

    @Test
    public void testAddVehicleToParking() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = parkingManagementService.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";

        // Act
        Optional<Ticket> ticketOptional = parkingEntranceAndExitManagementService.addVehicleToParking(parkingId, registrationNumber);

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
        UUID parkingId = parkingManagementService.createParking(parkingName, maxNumberOfSpaces, zipCode);

        // Act
        Optional<Ticket> ticketOptional = parkingEntranceAndExitManagementService.addVehicleToParking(parkingId);

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
        UUID parkingId = parkingManagementService.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = parkingEntranceAndExitManagementService.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();

        parkingPaymentManagementService.paymentOfTicketByCard(ticket.getId());

        // Act
        boolean isExitPermitted = parkingEntranceAndExitManagementService.vehicleExit(parkingId, ticket.getVehicle().getRegistrationNumber());

        // Assert
        Assert.assertTrue(isExitPermitted);
    }

    @Test
    public void testVehicleExitWithTicket() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = parkingManagementService.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = parkingEntranceAndExitManagementService.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();
        parkingPaymentManagementService.paymentOfTicketByCard(ticket.getId());

        // Act
        boolean isExitPermitted = parkingEntranceAndExitManagementService.vehicleExit(parkingId, ticket.getId());

        // Assert
        Assert.assertTrue(isExitPermitted);
    }


    @Test
    public void testGetTicketPrice1Min() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = parkingManagementService.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = parkingEntranceAndExitManagementService.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();
        ticket.setDateOfIssue(LocalDateTime.now().minusMinutes(1));

        // Act
        double ticketPrice = parkingTicketManagementService.getTicketPrice(ticket.getId());

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
        UUID parkingId = parkingManagementService.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = parkingEntranceAndExitManagementService.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();
        Plan dayPlan = new Plan();
        dayPlan.setPlanType(PlanType.DAYS);
        dayPlan.setPlanName("Day Plan");
        dayPlan.setPrice(5.0);
        parkingTicketManagementService.changeTicketPlan(ticket.getId(), dayPlan);
        ticket.setDateOfIssue(LocalDateTime.now().minusDays(1));

        // Act
        double ticketPrice = parkingPaymentManagementService.getTicketPrice(ticket.getId());

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
        UUID parkingId = parkingManagementService.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = parkingEntranceAndExitManagementService.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();

        Plan newPlan = new Plan();
        newPlan.setPlanName("New Plan");
        newPlan.setPlanType(PlanType.DAYS);
        newPlan.setId(UUID.randomUUID());
        newPlan.setPrice(5.0);

        // Act
        parkingTicketManagementService.changeTicketPlan(ticket.getId(), newPlan);

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
        UUID parkingId = parkingManagementService.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = parkingEntranceAndExitManagementService.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();

        Assert.assertNull(ticket.getDateOfPayment());

        // Act
        parkingPaymentManagementService.paymentOfTicketByCard(ticket.getId());

        // Assert
        Assert.assertNotNull(ticket.getDateOfPayment());
    }

    @Test
    public void testPaymentOfTicketByCash() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = parkingManagementService.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = parkingEntranceAndExitManagementService.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();
        double amountPaid = 10.0; // This should be more than the ticket price to test the change return

        // Act
        double change = parkingPaymentManagementService.paymentOfTicketByCash(ticket.getId(), amountPaid);

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
        UUID parkingId = parkingManagementService.createParking(parkingName, maxNumberOfSpaces, zipCode);
        String registrationNumber = "ABC123";
        Optional<Ticket> ticketOptional = parkingEntranceAndExitManagementService.addVehicleToParking(parkingId, registrationNumber);
        Ticket ticket = ticketOptional.get();

        SubscriptionType subscriptionType = new SubscriptionType();
        subscriptionType.setId(UUID.randomUUID());
        subscriptionType.setName("Monthly");
        subscriptionType.setPrice(50.0);

        // Act
        Subscription subscription = parkingSubscriptionManagementService.subscribeVehicle(registrationNumber, subscriptionType);

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
        UUID subscriptionId = parkingSubscriptionManagementService.createSubscriptionType(subscriptionTypeName, subscriptionTypePrice);

        // Assert
        Optional<SubscriptionType> subscriptionType = parkingSubscriptionManagementService.getSubscriptionTypeById(subscriptionId);
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

        UUID subscriptionTypeId = parkingSubscriptionManagementService.createSubscriptionType(subscriptionTypeName, originalPrice);
        Optional<SubscriptionType> originalSubscriptionType = parkingSubscriptionManagementService.getSubscriptionTypeById(subscriptionTypeId);

        // Act
        parkingSubscriptionManagementService.changeSubscriptionTypePrice(subscriptionTypeName, newPrice);

        // Assert
        SubscriptionType updatedSubscriptionType = parkingSubscriptionManagementService.getSubscriptionTypeById(subscriptionTypeId).get();
        Assert.assertNotNull(updatedSubscriptionType); // Check if subscription type is not null
        Assert.assertEquals(subscriptionTypeName, updatedSubscriptionType.getName()); // Check if the subscription type name is correct
        Assert.assertEquals(newPrice, updatedSubscriptionType.getPrice(), 0.01); // Check if the subscription type price is updated correctly
    }

    @Test
    public void testPaymentOfSubscriptionByCard() {
        // Arrange
        String subscriptionTypeName = "Monthly";
        double subscriptionTypePrice = 50.0;
        UUID subscriptionTypeId = parkingSubscriptionManagementService.createSubscriptionType(subscriptionTypeName, subscriptionTypePrice);
        Optional<SubscriptionType> subscriptionType = parkingSubscriptionManagementService.getSubscriptionTypeById(subscriptionTypeId);
        String registrationNumber = "ABC123";
        Subscription subscription = parkingSubscriptionManagementService.subscribeVehicle(registrationNumber, subscriptionType.get());

        // Act
        parkingPaymentManagementService.paymentOfSubscriptionByCard(subscription.getId());

        // Assert
        Subscription updatedSubscription = parkingSubscriptionManagementService.getSubscriptionById(subscription.getId());
        Assert.assertNotNull(updatedSubscription.getDateOfPurchase()); // Check if purchase date is set
    }

    @Test
    public void testMultipleCarsEnterParking() {
        // Arrange
        String parkingName = "Test Parking";
        int maxNumberOfSpaces = 100;
        String zipCode = "12345";
        UUID parkingId = parkingManagementService.createParking(parkingName, maxNumberOfSpaces, zipCode);

        // Act
        // Add 5 vehicles to parking
        for (int i = 0; i < 5; i++) {
            String registrationNumber = "ABC" + (i + 123); // Unique registration number for each vehicle
            Optional<Ticket> ticketOptional = parkingEntranceAndExitManagementService.addVehicleToParking(parkingId, registrationNumber);
            // Check if corresponding were created and not null
            Assert.assertTrue(ticketOptional.isPresent());
        }

        Optional<Parking> parking = parkingManagementService.getParkingById(parkingId);
        // Assert
        // Check if after entering of 5 vehicles, amount of available spaces was decreased correctly
        if (parking.isPresent()) {
            Assert.assertEquals(95, parking.get().getCurrentAvailableNumberOfSpaces());
        } else {
            Assert.fail("Parking not found");
        }
    }
}

