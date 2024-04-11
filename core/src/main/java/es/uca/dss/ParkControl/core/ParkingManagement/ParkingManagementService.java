package es.uca.dss.ParkControl.core.ParkingManagement;

import es.uca.dss.ParkControl.core.Parking.InMemoryParkingRepository;
import es.uca.dss.ParkControl.core.Parking.Parking;
import es.uca.dss.ParkControl.core.Parking.ParkingService;
import es.uca.dss.ParkControl.core.Plan.InMemoryPlanRepository;
import es.uca.dss.ParkControl.core.Plan.PlanService;
import es.uca.dss.ParkControl.core.Plan.PlanType;
import es.uca.dss.ParkControl.core.Record.InMemoryRecordRepository;
import es.uca.dss.ParkControl.core.Record.Record;
import es.uca.dss.ParkControl.core.Record.RecordService;
import es.uca.dss.ParkControl.core.Subscription.InMemorySubscriptionRepository;
import es.uca.dss.ParkControl.core.Subscription.Subscription;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionService;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionType;
import es.uca.dss.ParkControl.core.Ticket.InMemoryTicketRepository;
import es.uca.dss.ParkControl.core.Ticket.Ticket;
import es.uca.dss.ParkControl.core.Ticket.TicketService;
import es.uca.dss.ParkControl.core.Transaction.InMemoryTransactionRepository;
import es.uca.dss.ParkControl.core.Transaction.Transaction;
import es.uca.dss.ParkControl.core.Transaction.TransactionService;
import es.uca.dss.ParkControl.core.Vehicle.InMemoryVehicleRepository;
import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import es.uca.dss.ParkControl.core.Vehicle.VehicleService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

public class ParkingManagementService {

    // Creating the services to manage the data
    private ParkingService parkingService;
    private PlanService planService;
    private RecordService recordService;
    private SubscriptionService subscriptionService;
    private TicketService ticketService;
    private TransactionService transactionService;
    private VehicleService vehicleService;

    // Creating the repositories to save the data
    InMemoryParkingRepository parkingRepository = new InMemoryParkingRepository();
    InMemorySubscriptionRepository subscriptionRepository = new InMemorySubscriptionRepository();
    InMemoryPlanRepository planRepository = new InMemoryPlanRepository();
    InMemoryTicketRepository ticketRepository = new InMemoryTicketRepository();
    InMemoryTransactionRepository transactionRepository = new InMemoryTransactionRepository();
    InMemoryVehicleRepository vehicleRepository = new InMemoryVehicleRepository();
    InMemoryRecordRepository recordRepository = new InMemoryRecordRepository();


    public ParkingManagementService() {
        // Creating the services to manage the data
        this.parkingService = new ParkingService(parkingRepository);
        this.planService = new PlanService(planRepository);
        this.subscriptionService = new SubscriptionService(subscriptionRepository);
        this.ticketService = new TicketService(ticketRepository);
        this.transactionService = new TransactionService(transactionRepository);
        this.vehicleService = new VehicleService(vehicleRepository);
        this.recordService = new RecordService(recordRepository);
    }

    // Method to create a parking
    public void createParking(String name, int maxNumberOfSpaces, String zipCode) {
        Parking parking = new Parking();
        parking.setId(UUID.randomUUID());
        parking.setName(name);
        parking.setMaxNumberOfSpaces(maxNumberOfSpaces);
        parking.setZipCode(zipCode);
        parkingService.saveParking(parking);
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
        Ticket ticket = ticketService.getLatestTicket(registrationNumber);
        if (ticket == null) {
            return false;
        }
        if (ticket.getParking().getId() != parkingId) {
            return false;
        }
        if (ticket.getDateOfPayment() != null || ticket.getDateOfPayment().isBefore(LocalDateTime.now())) {
            return false;
        }
        removeVehicleFromParking(parkingId, registrationNumber);
        return true;
    }

    public boolean vehicleExit(UUID parkingId, Ticket ticket) {
        if (ticket == null) {
            return false;
        }
        if (ticket.getParking().getId() != parkingId) {
            return false;
        }
        if (ticket.getDateOfPayment() != null || !(ticket.getDateOfPayment().isBefore(LocalDateTime.now().minusMinutes(10)))) {
            return false;
        }
        removeVehicleFromParking(parkingId, ticket.getVehicle().getRegistrationNumber());
        return true;
    }

    private void removeVehicleFromParking(UUID parkingId, String registrationNumber) {
        Vehicle vehicle = vehicleService.getVehicleByRegistrationNumber(registrationNumber);
        parkingService.removeVehicleFromParking(parkingId, vehicle);
    }

    public double getTicketPrice(UUID ticketId) {
        Ticket ticket = ticketService.getTicket(ticketId);
        LocalDateTime dateOfEntry = ticket.getDateOfIssue();
        LocalDateTime tempDateTime = LocalDateTime.from(dateOfEntry);
        PlanType ticketPlanType = getTicketPlanType(ticket);
        double ticketPlanTypePrice = ticketPlanType.getValue();
        int weeks = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.WEEKS);
        int days = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.DAYS);
        int hours = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.HOURS);
        long minutes = tempDateTime.until(LocalDateTime.now(), ChronoUnit.MINUTES);
        if (ticketPlanType == PlanType.WEEKS){
            return weeks * ticketPlanTypePrice + days * ticketPlanTypePrice / 7.0 + hours * ticketPlanTypePrice / 168.0;
        } else if (ticketPlanType == PlanType.DAYS) {
            return days * ticketPlanTypePrice + hours * ticketPlanTypePrice / 24.0;
        }
        else if (ticketPlanType == PlanType.HOURS){
            return hours * ticketPlanTypePrice + minutes * ticketPlanTypePrice / 60.0;
        }
        else return minutes*ticketPlanTypePrice;
    }

    private PlanType getTicketPlanType(Ticket ticket){
        LocalDateTime dateOfEntry = ticket.getDateOfIssue();
        LocalDateTime tempDateTime = LocalDateTime.from(dateOfEntry);
        int weeks = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.WEEKS);
        int days = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.DAYS);
        int hours = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.HOURS);
        if (weeks >= 1){
            return PlanType.WEEKS;
        } else if (days >= 1) {
            return PlanType.DAYS;
        } else if (hours >=1) {
            return PlanType.HOURS;
        }
        else return PlanType.MINUTES;
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
        if (amount < ticketPrice) {
            return amount - ticketPrice;
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
        }
        else {
            subscription.setVehicle(vehicle);
        }
        subscription.setId(UUID.randomUUID());
        subscription.setSubscriptionType(subscriptionType);
        subscription.setDateOfPurchase(LocalDateTime.now());
        return subscription;
    }

    public void changeSubscriptionTypePrice(SubscriptionType subscriptionType, double newPrice) {

        // TODO implement
    }

}
