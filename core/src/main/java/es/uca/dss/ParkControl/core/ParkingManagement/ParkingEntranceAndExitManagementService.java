package es.uca.dss.ParkControl.core.ParkingManagement;

import es.uca.dss.ParkControl.core.Parking.Parking;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for managing parking entrance and exit operations.
 */
@Service
public class ParkingEntranceAndExitManagementService {
    private final PlanService planService;
    private ParkingService parkingService;
    private VehicleService vehicleService;
    private TicketService ticketService;
    private RecordService recordService;

    private SubscriptionService subscriptionService;

    /**
     * Constructor for the ParkingEntranceAndExitManagementService.
     *
     * @param parkingRepository      the parking repository
     * @param vehicleRepository      the vehicle repository
     * @param ticketRepository       the ticket repository
     * @param recordRepository       the record repository
     * @param subscriptionRepository the subscription repository
     * @param planRepository         the plan repository
     */
    public ParkingEntranceAndExitManagementService(ParkingRepository parkingRepository, VehicleRepository vehicleRepository, TicketRepository ticketRepository, RecordRepository recordRepository, SubscriptionRepository subscriptionRepository, PlanRepository planRepository) {
        this.parkingService = new ParkingService(parkingRepository);
        this.vehicleService = new VehicleService(vehicleRepository);
        this.ticketService = new TicketService(ticketRepository);
        this.recordService = new RecordService(recordRepository);
        this.subscriptionService = new SubscriptionService(subscriptionRepository);
        this.planService = new PlanService(planRepository);
    }


    /**
     * Method to simulate a vehicle with registration number entering the parking.
     *
     * @param parkingId          the parking id
     * @param registrationNumber the registration number of the vehicle
     * @return the ticket for the vehicle
     */
    public Optional<Ticket> addVehicleToParking(UUID parkingId, String registrationNumber) {
        Optional<Parking> optionalParking = parkingService.getParkingById(parkingId);
        if (optionalParking.isPresent() && optionalParking.get().getCurrentAvailableNumberOfSpaces() > 0) {
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
            ticket.setParking(optionalParking.get());
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
            record.setParking(optionalParking.get());
            recordService.createRecord(record);
            return Optional.of(ticket);
        }
        return Optional.empty();
    }

    /**
     * Method to simulate a vehicle without registration number entering the parking.
     *
     * @param parkingId the parking id
     * @return the ticket for the vehicle
     */
    public Optional<Ticket> addVehicleToParking(UUID parkingId) {
        Optional<Parking> optionalParking = parkingService.getParkingById(parkingId);
        if (optionalParking.isPresent() && optionalParking.get().getCurrentAvailableNumberOfSpaces() > 0) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(UUID.randomUUID());
            vehicleService.createVehicle(vehicle);
            parkingService.addVehicleToParking(parkingId, vehicle);
            Ticket ticket = new Ticket();
            ticket.setId(UUID.randomUUID());
            ticket.setVehicle(vehicle);
            ticket.setParking(optionalParking.get());
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
            record.setParking(optionalParking.get());
            recordService.createRecord(record);
            return Optional.of(ticket);
        }
        return Optional.empty();
    }

    /**
     * Method to simulate a vehicle with registration number exiting the parking.
     *
     * @param parkingId                 the parking id
     * @param vehicleRegistrationNumber the registration number of the vehicle
     * @return true if the exit is permitted, false otherwise
     */
    public boolean vehicleExit(UUID parkingId, String vehicleRegistrationNumber) {
        boolean isExitPermitted = false;
        Optional<Vehicle> optionalVehicle = Optional.ofNullable(vehicleService.getVehicleByRegistrationNumber(vehicleRegistrationNumber));
        if (optionalVehicle.isPresent()) {
            UUID vehicleId = optionalVehicle.get().getId();
            Optional<Ticket> optionalTicket = Optional.ofNullable(ticketService.getLatestTicket(vehicleId));
            if (optionalTicket.isPresent()) {
                boolean isIdEqual = optionalTicket.get().getParking().getId().equals(parkingId);
                if (optionalTicket.get().getDateOfPayment().equals(null)) {
                    return isExitPermitted;
                }
                boolean isDateBefore = optionalTicket.get().getDateOfPayment().isBefore(LocalDateTime.now().plusMinutes(10));
                if (isIdEqual && isDateBefore) {
                    isExitPermitted = true;
                    removeVehicleFromParking(optionalTicket.get().getParking().getId(), vehicleId);
                    return isExitPermitted;
                }
                if (subscriptionService.isValidSubscriptionAvailable(vehicleId)) {
                    isExitPermitted = true;
                    removeVehicleFromParking(optionalTicket.get().getParking().getId(), vehicleId);
                    return isExitPermitted;
                }
            }
        }
        return isExitPermitted;
    }

    /**
     * Method to simulate a vehicle with ticket exiting the parking.
     *
     * @param parkingId the parking id
     * @param ticketId  the ticket id
     * @return true if the exit is permitted, false otherwise
     */
    public boolean vehicleExit(UUID parkingId, UUID ticketId) {
        boolean isExitPermitted = false;
        Ticket ticket = ticketService.getTicket(ticketId);
        if (ticket != null) {
            if (ticket.getDateOfPayment() == null) {
                return isExitPermitted;
            }
            boolean isDateBefore = ticket.getDateOfPayment().isBefore(LocalDateTime.now().plusMinutes(10));
            if (ticket.getParking().getId().equals(parkingId) && isDateBefore) {
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
        Optional<Vehicle> optionalVehicle = Optional.ofNullable(vehicleService.getVehicle(vehicleId));
        List<Record> records = recordService.findByVehicle(optionalVehicle.get().getId());
        Record record = records.get(records.size() - 1);
        record.setDateOfExit(LocalDateTime.now());
        recordService.createRecord(record);
        parkingService.removeVehicleFromParking(parkingId, optionalVehicle.get());
    }

}
