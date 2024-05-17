package es.uca.dss.ParkControl.core.ParkingManagement;

import es.uca.dss.ParkControl.core.Parking.ParkingRepository;
import es.uca.dss.ParkControl.core.Parking.ParkingService;
import es.uca.dss.ParkControl.core.Plan.Plan;
import es.uca.dss.ParkControl.core.Plan.PlanRepository;
import es.uca.dss.ParkControl.core.Plan.PlanService;
import es.uca.dss.ParkControl.core.Plan.PlanType;
import es.uca.dss.ParkControl.core.Record.Record;
import es.uca.dss.ParkControl.core.Record.RecordRepository;
import es.uca.dss.ParkControl.core.Record.RecordService;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionRepository;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionService;
import es.uca.dss.ParkControl.core.Ticket.Ticket;
import es.uca.dss.ParkControl.core.Ticket.TicketRepository;
import es.uca.dss.ParkControl.core.Ticket.TicketService;
import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import es.uca.dss.ParkControl.core.Vehicle.VehicleRepository;
import es.uca.dss.ParkControl.core.Vehicle.VehicleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingEntranceAndExitManagementService {
    private final PlanService planService;
    private ParkingService parkingService;
    private VehicleService vehicleService;
    private TicketService ticketService;
    private RecordService recordService;

    private SubscriptionService subscriptionService;

    public ParkingEntranceAndExitManagementService(ParkingRepository parkingRepository, VehicleRepository vehicleRepository, TicketRepository ticketRepository, RecordRepository recordRepository, SubscriptionRepository subscriptionRepository, PlanRepository planRepository) {
        this.parkingService = new ParkingService(parkingRepository);
        this.vehicleService = new VehicleService(vehicleRepository);
        this.ticketService = new TicketService(ticketRepository);
        this.recordService = new RecordService(recordRepository);
        this.subscriptionService = new SubscriptionService(subscriptionRepository);
        this.planService = new PlanService(planRepository);
    }


    // Method to simulate a vehicle with registration number entering the parking
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
        planService.createPlan(plan);
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

    // Method to simulate a vehicle without registration number entering the parking
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
        planService.createPlan(plan);
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

    // Method to simulate a vehicle with registration number exiting the parking
    public boolean vehicleExit(UUID parkingId, String vehicleRegistrationNumber) {
        boolean isExitPermitted = false;
        UUID vehicleId = vehicleService.getVehicleByRegistrationNumber(vehicleRegistrationNumber).getId();
        Ticket ticket = ticketService.getLatestTicket(vehicleId);
        if (ticket != null) {
            boolean isIdEqual = ticket.getParking().getId().equals(parkingId);
            boolean isDateBefore = ticket.getDateOfPayment().isBefore(LocalDateTime.now().plusMinutes(10));
            if (isIdEqual && isDateBefore) {
                isExitPermitted = true;
                removeVehicleFromParking(parkingId, vehicleId);
                return isExitPermitted;
            }
            if (subscriptionService.isValidSubscriptionAvailable(vehicleId)) {
                isExitPermitted = true;
                removeVehicleFromParking(parkingId, vehicleId);
                return isExitPermitted;
            }
        }
        return isExitPermitted;
    }

    // Method to simulate a vehicle with ticket exiting the parking
    public boolean vehicleExit(UUID parkingId, Ticket ticket) {
        boolean isExitPermitted = false;
        if (ticket != null) {
            boolean isDateBefore = ticket.getDateOfPayment().isBefore(LocalDateTime.now().plusMinutes(10));
            if (ticket.getParking().getId().equals(parkingId) && isDateBefore)
            {
                isExitPermitted = true;
                removeVehicleFromParking(parkingId, ticket.getVehicle().getId());
                return isExitPermitted;
            }
            if (subscriptionService.isValidSubscriptionAvailable(ticket.getVehicle().getRegistrationNumber())) {
                isExitPermitted = true;
                removeVehicleFromParking(parkingId, ticket.getVehicle().getId());
                return isExitPermitted;
            }
        }
        return isExitPermitted;
    }

    // Inner method to remove a vehicle from the parking
    private void removeVehicleFromParking(UUID parkingId, UUID vehicleId) {
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        parkingService.removeVehicleFromParking(parkingId, vehicle);
    }

}
