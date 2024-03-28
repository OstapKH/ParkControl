package es.uca.dss.ParkControl.core.ParkingManagement;

import es.uca.dss.ParkControl.core.Parking.InMemoryParkingRepository;
import es.uca.dss.ParkControl.core.Parking.Parking;
import es.uca.dss.ParkControl.core.Parking.ParkingService;
import es.uca.dss.ParkControl.core.Plan.InMemoryPlanRepository;
import es.uca.dss.ParkControl.core.Plan.PlanService;
import es.uca.dss.ParkControl.core.Record.InMemoryRecordRepository;
import es.uca.dss.ParkControl.core.Record.Record;
import es.uca.dss.ParkControl.core.Record.RecordService;
import es.uca.dss.ParkControl.core.Report.InMemoryReportRepository;
import es.uca.dss.ParkControl.core.Report.Report;
import es.uca.dss.ParkControl.core.Report.ReportService;
import es.uca.dss.ParkControl.core.Subscription.InMemorySubscriptionRepository;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionService;
import es.uca.dss.ParkControl.core.Ticket.InMemoryTicketRepository;
import es.uca.dss.ParkControl.core.Ticket.Ticket;
import es.uca.dss.ParkControl.core.Ticket.TicketService;
import es.uca.dss.ParkControl.core.Transaction.InMemoryTransactionRepository;
import es.uca.dss.ParkControl.core.Transaction.TransactionService;
import es.uca.dss.ParkControl.core.Vehicle.InMemoryVehicleRepository;
import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import es.uca.dss.ParkControl.core.Vehicle.VehicleService;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
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

    public void createParking(String name, int maxNumberOfSpaces, String zipCode) {
        Parking parking = new Parking();
        parking.setId(UUID.randomUUID());
        parking.setName(name);
        parking.setMaxNumberOfSpaces(maxNumberOfSpaces);
        parking.setZipCode(zipCode);
        parkingService.saveParking(parking);
    }

    public void changeParkingName(UUID id, String newName) {
        parkingService.changeParkingName(id, newName);
    }

    public void changeParkingAmountOfSpaces(UUID id, int newAmountOfSpaces) {
        parkingService.changeParkingMaxSpaces(id, newAmountOfSpaces);
    }

    public Ticket addVehicleToParking(UUID parkingId, String registrationNumber) {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber(registrationNumber);
        vehicle.setId(UUID.randomUUID());
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
        return ticket;
    }

    public Ticket addVehicleToParking(UUID parkingId) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(UUID.randomUUID());
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
        return ticket;
    }

    public void removeVehicleFromParking(UUID parkingId, String registrationNumber) {
        Vehicle vehicle = vehicleService.getVehicleByRegistrationNumber(registrationNumber);
        parkingService.removeVehicleFromParking(parkingId, vehicle);
    }
}
