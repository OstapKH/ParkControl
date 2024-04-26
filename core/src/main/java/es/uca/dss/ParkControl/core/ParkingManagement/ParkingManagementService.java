package es.uca.dss.ParkControl.core.ParkingManagement;

import es.uca.dss.ParkControl.core.Parking.InMemoryParkingRepository;
import es.uca.dss.ParkControl.core.Parking.Parking;
import es.uca.dss.ParkControl.core.Parking.ParkingService;
import es.uca.dss.ParkControl.core.Plan.InMemoryPlanRepository;
import es.uca.dss.ParkControl.core.Plan.Plan;
import es.uca.dss.ParkControl.core.Plan.PlanService;
import es.uca.dss.ParkControl.core.Plan.PlanType;
import es.uca.dss.ParkControl.core.Record.InMemoryRecordRepository;
import es.uca.dss.ParkControl.core.Record.Record;
import es.uca.dss.ParkControl.core.Record.RecordService;
import es.uca.dss.ParkControl.core.Subscription.*;
import es.uca.dss.ParkControl.core.Ticket.InMemoryTicketRepository;
import es.uca.dss.ParkControl.core.Ticket.Ticket;
import es.uca.dss.ParkControl.core.Ticket.TicketService;
import es.uca.dss.ParkControl.core.Transaction.InMemoryTransactionRepository;
import es.uca.dss.ParkControl.core.Transaction.Transaction;
import es.uca.dss.ParkControl.core.Transaction.TransactionService;
import es.uca.dss.ParkControl.core.Vehicle.InMemoryVehicleRepository;
import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import es.uca.dss.ParkControl.core.Vehicle.VehicleService;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public class ParkingManagementService {

    // Creating the services to manage the data
    private ParkingService parkingService;
    private PlanService planService;
    private RecordService recordService;
    private SubscriptionService subscriptionService;
    private TicketService ticketService;
    private TransactionService transactionService;
    private VehicleService vehicleService;

    private SubscriptionTypeService subscriptionTypeService;

    // Creating the repositories to save the data
    InMemoryParkingRepository parkingRepository = new InMemoryParkingRepository();
    InMemorySubscriptionRepository subscriptionRepository = new InMemorySubscriptionRepository();
    InMemoryPlanRepository planRepository = new InMemoryPlanRepository();
    InMemoryTicketRepository ticketRepository = new InMemoryTicketRepository();
    InMemoryTransactionRepository transactionRepository = new InMemoryTransactionRepository();
    InMemoryVehicleRepository vehicleRepository = new InMemoryVehicleRepository();
    InMemoryRecordRepository recordRepository = new InMemoryRecordRepository();

    InMemorySubscriptionTypeRepository subscriptionTypeRepository = new InMemorySubscriptionTypeRepository();


    public ParkingManagementService() {
        // Creating the services to manage the data
        this.parkingService = new ParkingService(parkingRepository);
        this.planService = new PlanService(planRepository);
        this.subscriptionService = new SubscriptionService(subscriptionRepository);
        this.ticketService = new TicketService(ticketRepository);
        this.transactionService = new TransactionService(transactionRepository);
        this.vehicleService = new VehicleService(vehicleRepository);
        this.recordService = new RecordService(recordRepository);
        this.subscriptionTypeService = new SubscriptionTypeService(subscriptionTypeRepository);
    }

    // Method to create a parking
    public UUID createParking(String name, int maxNumberOfSpaces, String zipCode) {
        Parking parking = new Parking();
        parking.setId(UUID.randomUUID());
        parking.setName(name);
        parking.setMaxNumberOfSpaces(maxNumberOfSpaces);
        parking.setCurrentAvailableNumberOfSpaces(maxNumberOfSpaces);
        parking.setZipCode(zipCode);
        parkingService.saveParking(parking);
        return parking.getId();
    }

    // Method to change parking details
    public void changeParkingDetails(UUID id, String newName, int newAmountOfSpaces, String newZipCode) {
        Parking parking = parkingService.getParkingById(id);
        if (parking != null) {
            parking.setName(newName);
            parking.setZipCode(newZipCode);
            parking.setMaxNumberOfSpaces(newAmountOfSpaces);
        }
    }


    // Method to simulate a vehicle entering the parking
    public Optional<Ticket> addVehicleToParking(UUID parkingId, String registrationNumber) {
        if (parkingService.getParkingById(parkingId).getCurrentAvailableNumberOfSpaces() <= 0) {
            return Optional.empty();
        }
        Vehicle vehicle = vehicleService.getVehicleByRegistrationNumber(registrationNumber);
        if (vehicle == null) {
            vehicle = new Vehicle();
            vehicle.setId(UUID.randomUUID());
            vehicle.setRegistrationNumber(registrationNumber);
            vehicleService.createVehicle(vehicle);
        }
        parkingService.addVehicleToParking(parkingId, vehicle);
        Ticket ticket = new Ticket();
        ticket.setId(UUID.randomUUID());
        ticket.setVehicle(vehicle);
        ticket.setParking(parkingService.getParkingById(parkingId));
        ticket.setDateOfIssue(LocalDateTime.now());
        Plan plan = new Plan();
        plan.setPlanName("Basic");
        plan.setPlanType(PlanType.MINUTES);
        plan.setId(UUID.randomUUID());
        plan.setPrice(0.2);
        ticket.setPlan(plan);
        ticketService.createTicket(ticket);
        Record record = new Record();
        record.setId(UUID.randomUUID());
        record.setTicket(ticket);
        record.setDateOfEntry(LocalDateTime.now());
        record.setParking(parkingService.getParkingById(parkingId));
        recordService.createRecord(record);
        return Optional.of(ticket);
    }

    public Optional<Ticket> addVehicleToParking(UUID parkingId) {
        if (parkingService.getParkingById(parkingId).getCurrentAvailableNumberOfSpaces() <= 0) {
            return Optional.empty();
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setId(UUID.randomUUID());
        vehicleService.createVehicle(vehicle);
        parkingService.addVehicleToParking(parkingId, vehicle);
        Ticket ticket = new Ticket();
        ticket.setId(UUID.randomUUID());
        ticket.setVehicle(vehicle);
        ticket.setParking(parkingService.getParkingById(parkingId));
        ticket.setDateOfIssue(LocalDateTime.now());
        Plan plan = new Plan();
        plan.setPlanName("Basic");
        plan.setPlanType(PlanType.MINUTES);
        plan.setId(UUID.randomUUID());
        plan.setPrice(0.2);
        ticket.setPlan(plan);
        ticketService.createTicket(ticket);
        Record record = new Record();
        record.setId(UUID.randomUUID());
        record.setTicket(ticket);
        record.setDateOfEntry(LocalDateTime.now());
        record.setParking(parkingService.getParkingById(parkingId));
        recordService.createRecord(record);
        return Optional.of(ticket);
    }

    public boolean vehicleExit(UUID parkingId, String registrationNumber) {
        boolean isExitPermitted = false;
        Ticket ticket = ticketService.getLatestTicket(registrationNumber);
        if (ticket != null) {
            boolean isIdEqual = ticket.getParking().getId().equals(parkingId);
            boolean isDateBefore = ticket.getDateOfPayment().isBefore(LocalDateTime.now().plusMinutes(10));
            if (ticket.getParking().getId().equals(parkingId) && ticket.getDateOfPayment().isBefore(LocalDateTime.now().plusMinutes(10))){
                isExitPermitted = true;
                removeVehicleFromParking(parkingId, registrationNumber);
                return isExitPermitted;
            }
            if (subscriptionService.isValidSubscriptionAvailable(registrationNumber)){
                isExitPermitted = true;
                removeVehicleFromParking(parkingId, registrationNumber);
                return isExitPermitted;
            }
        }
        return isExitPermitted;
    }

    public boolean vehicleExit(UUID parkingId, Ticket ticket) {
        boolean isExitPermitted = false;
        if (ticket != null) {
            if (ticket.getParking().getId().equals(parkingId) && ticket.getDateOfPayment().isBefore(LocalDateTime.now().plusMinutes(10))){
                isExitPermitted = true;
                removeVehicleFromParking(parkingId, ticket.getVehicle().getRegistrationNumber());
                return isExitPermitted;
            }
            if (subscriptionService.isValidSubscriptionAvailable(ticket.getVehicle().getRegistrationNumber())){
                isExitPermitted = true;
                removeVehicleFromParking(parkingId, ticket.getVehicle().getRegistrationNumber());
                return isExitPermitted;
            }
        }
        return isExitPermitted;
    }

    private void removeVehicleFromParking(UUID parkingId, String registrationNumber) {
        Vehicle vehicle = vehicleService.getVehicleByRegistrationNumber(registrationNumber);
        parkingService.removeVehicleFromParking(parkingId, vehicle);
    }

    public double getTicketPrice(UUID ticketId) {
        Ticket ticket = ticketService.getTicket(ticketId);
        // Based on the time the vehicle has been parked, we set the plan type
        ticket.getPlan().setPlanType(calculateTicketPlanType(ticket));
        LocalDateTime dateOfEntry = ticket.getDateOfIssue();
        LocalDateTime tempDateTime = LocalDateTime.from(dateOfEntry);
        double ticketPlanPrice = ticket.getPlan().getPrice();
        PlanType ticketPlanType = ticket.getPlan().getPlanType();
        int weeks = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.WEEKS);
        int days = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.DAYS);
        int hours = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.HOURS);
        long minutes = tempDateTime.until(LocalDateTime.now(), ChronoUnit.MINUTES);
        if (ticketPlanType == PlanType.WEEKS) {
            return weeks * ticketPlanPrice + days * ticketPlanPrice / 7.0 + hours * ticketPlanPrice / 168.0;
        } else if (ticketPlanType == PlanType.DAYS) {
            return days * ticketPlanPrice + (hours - days*24) * ticketPlanPrice / 24.0;
        } else if (ticketPlanType == PlanType.HOURS) {
            return hours * ticketPlanPrice + minutes * ticketPlanPrice / 60.0;
        } else return minutes * ticketPlanPrice;
    }

    public void changeTicketPlan(UUID ticketId, Plan newPlan){
        Ticket ticket = ticketService.getTicket(ticketId);
        ticket.setPlan(newPlan);
    }
    private PlanType calculateTicketPlanType(Ticket ticket) {
        LocalDateTime dateOfEntry = ticket.getDateOfIssue();
        LocalDateTime tempDateTime = LocalDateTime.from(dateOfEntry);
        int weeks = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.WEEKS);
        int days = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.DAYS);
        int hours = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.HOURS);
        if (weeks >= 1) {
            return PlanType.WEEKS;
        } else if (days >= 1) {
            return PlanType.DAYS;
        } else if (hours >= 1) {
            return PlanType.HOURS;
        } else return PlanType.MINUTES;
    }

    public void paymentOfTicketByCard(UUID ticketId) {
        Ticket ticket = ticketService.getTicket(ticketId);
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAmountOfPayment(getTicketPrice(ticketId));
        transaction.setConceptID(ticketId);
        // We suppose that payment is successful and done by card
        transaction.setDone(true);
        transactionService.createTransaction(transaction);
        ticket.setDateOfPayment(LocalDateTime.now());
    }

    public double paymentOfTicketByCash(UUID ticketId, double amount) {
        Ticket ticket = ticketService.getTicket(ticketId);
        double ticketPrice = getTicketPrice(ticketId);
        // If user pays more than the ticket price, we return the change
        if (amount < ticketPrice) {
            return ticketPrice - amount;
        }
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAmountOfPayment(amount);
        transaction.setConceptID(ticketId);
        transaction.setDone(true);
        transactionService.createTransaction(transaction);
        ticket.setDateOfPayment(LocalDateTime.now());
        return amount - ticketPrice;
    }

    public Subscription subscribeVehicle(String registrationNumber, SubscriptionType subscriptionType) {
        Subscription subscription = new Subscription();
        Vehicle vehicle = vehicleService.getVehicleByRegistrationNumber(registrationNumber);
        if (vehicle == null) {
            vehicle = new Vehicle();
            vehicle.setId(UUID.randomUUID());
            vehicle.setRegistrationNumber(registrationNumber);
            vehicleService.createVehicle(vehicle);
        } else {
            subscription.setVehicle(vehicle);
        }
        subscription.setId(UUID.randomUUID());
        subscription.setSubscriptionType(subscriptionType);
        subscription.setDateOfPurchase(LocalDateTime.now());
        subscriptionService.createSubscription(subscription);
        return subscription;
    }

    public UUID createSubscriptionType(String name, double price) {
    SubscriptionType subscriptionType = new SubscriptionType();
    // TODO Ask about this method
    subscriptionType.setId(UUID.randomUUID());
    subscriptionType.setName(name);
    subscriptionType.setPrice(price);
    subscriptionTypeService.saveSubscriptionType(subscriptionType);
    return subscriptionType.getId();
}

    public void changeSubscriptionTypePrice(String name, double newPrice) {
        SubscriptionType subscriptionType = subscriptionTypeService.getSubscriptionByName(name);
        subscriptionType.setPrice(newPrice);
        subscriptionTypeService.saveSubscriptionType(subscriptionType);
    }

    public void paymentOfSubscriptionByCard(UUID subscriptionId) {
        Subscription subscription = subscriptionService.getSubscription(subscriptionId);
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAmountOfPayment(subscription.getSubscriptionType().getPrice());
        transaction.setConceptID(subscriptionId);
        // We suppose that payment is successful and done by card
        transaction.setDone(true);
        transactionService.createTransaction(transaction);
        subscription.setDateOfPurchase(LocalDateTime.now());
    }

    public double paymentOfSubscriptionByCash(UUID subscriptionId, double amount) {
        Subscription subscription = subscriptionService.getSubscription(subscriptionId);
        double subscriptionPrice = subscription.getSubscriptionType().getPrice();
        // If user pays more than the ticket price, we return the change
        if (amount < subscriptionPrice) {
            return subscriptionPrice - amount;
        }
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAmountOfPayment(amount);
        transaction.setConceptID(subscriptionId);
        transaction.setDone(true);
        transactionService.createTransaction(transaction);
        subscription.setDateOfPurchase(LocalDateTime.now());
        return amount - subscriptionPrice;
    }

    public Optional<List<Record>> getEntriesStatisticByDay(UUID parkingId, LocalDateTime dayDate){
        return Optional.of(recordService.getEntriesByDay(parkingId, dayDate));
    }

    public Optional<List<Record>> getExitsStatisticByDay(UUID parkingId, LocalDateTime dayDate){
        return Optional.of(recordService.getExitsByDay(parkingId, dayDate));
    }

    public Optional<List<Record>> getEntriesStatisticByMonth(UUID parkingId, LocalDateTime monthDate){
        return Optional.of(recordService.getEntriesByMonth(parkingId, monthDate));
    }

    public Optional<List<Record>> getExitsStatisticByMonth(UUID parkingId, LocalDateTime monthDate){
        return Optional.of(recordService.getExitsByMonth(parkingId, monthDate));
    }

    public Parking getParkingById(UUID parkingId) {
        return parkingService.getParkingById(parkingId);
    }
    // TODO Check if Optional would be suitable here.
    // Especially check if Optional returns empty in case if ArrayList has 0 members

    public SubscriptionType getSubscriptionTypeById(UUID id) {
        return subscriptionTypeService.getSubscription(id);
    }

    public Subscription getSubscriptionById(UUID id) {
        return subscriptionService.getSubscription(id);
    }
}
