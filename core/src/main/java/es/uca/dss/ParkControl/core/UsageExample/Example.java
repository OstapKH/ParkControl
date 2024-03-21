package es.uca.dss.ParkControl.core.UsageExample;

import es.uca.dss.ParkControl.core.Parking.InMemoryParkingRepository;
import es.uca.dss.ParkControl.core.Parking.Parking;
import es.uca.dss.ParkControl.core.Plan.InMemoryPlanRepository;
import es.uca.dss.ParkControl.core.Plan.Plan;
import es.uca.dss.ParkControl.core.Plan.PlanType;
import es.uca.dss.ParkControl.core.Report.InMemoryReportRepository;
import es.uca.dss.ParkControl.core.Report.Report;
import es.uca.dss.ParkControl.core.Report.ReportService;
import es.uca.dss.ParkControl.core.Subscription.InMemorySubscriptionRepository;
import es.uca.dss.ParkControl.core.Subscription.Subscription;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionType;
import es.uca.dss.ParkControl.core.Ticket.InMemoryTicketRepository;
import es.uca.dss.ParkControl.core.Ticket.Ticket;
import es.uca.dss.ParkControl.core.Ticket.TicketService;
import es.uca.dss.ParkControl.core.Transaction.InMemoryTransactionRepository;
import es.uca.dss.ParkControl.core.Transaction.Transaction;
import es.uca.dss.ParkControl.core.Transaction.TransactionService;
import es.uca.dss.ParkControl.core.Vehicle.InMemoryVehicleRepository;
import es.uca.dss.ParkControl.core.Vehicle.Vehicle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Example {
    // This is an example of how to use the core classes
    public static void main(String[] args) {
        // Creating the repositories
        InMemoryParkingRepository parkingRepository = new InMemoryParkingRepository();
        InMemorySubscriptionRepository subscriptionRepository = new InMemorySubscriptionRepository();
        InMemoryPlanRepository planRepository = new InMemoryPlanRepository();
        InMemoryReportRepository reportRepository = new InMemoryReportRepository();
        InMemoryTicketRepository ticketRepository = new InMemoryTicketRepository();
        InMemoryTransactionRepository transactionRepository = new InMemoryTransactionRepository();
        InMemoryVehicleRepository vehicleRepository = new InMemoryVehicleRepository();

        List<Vehicle> allocatedVehicles = new ArrayList<>();
        // Generating 25 random vehicles and adding them to the list of allocated vehicles
        for (int i = 0; i < 25; i++) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(UUID.randomUUID());
            vehicle.setRegistrationNumber("12"+ String.valueOf(i) + "4ABC");
            vehicleRepository.save(vehicle);
            allocatedVehicles.add(vehicle);
        }

        //Creating a parking and adding it to the repository
        Parking parking = new Parking();
        parking.setId(UUID.randomUUID());
        parking.setName("Parking ESI");
        parking.setMaxNumberOfSpaces(200);
        parking.setZipCode("11123");
        parking.setAllocatedVehicles(allocatedVehicles);
        parkingRepository.save(parking);

        //Creating plans and adding them to the repository
        Plan planHour = new Plan();
        planHour.setId(UUID.randomUUID());
        planHour.setPlanName("Plan ESI hour");
        planHour.setPrice(0.5);
        planHour.setPlanType(PlanType.HOURS);
        planRepository.save(planHour);

        Plan planDay = new Plan();
        planDay.setId(UUID.randomUUID());
        planDay.setPlanName("Plan ESI day");
        planDay.setPrice(2.5);
        planDay.setPlanType(PlanType.DAYS);
        planRepository.save(planDay);

        // Creating a subcription and adding it to the repository
        Subscription subscription = new Subscription();
        subscription.setId(UUID.randomUUID());
        // We suppose that car with "1214ABC" registration number is a subscriber of MONTH type
        subscription.setVehicle(vehicleRepository.findByRegistrationNumber("1214ABC"));
        subscription.setSubscriptionType(SubscriptionType.MONTH);
        subscription.setDateOfPurchase(LocalDate.parse("2023-02-01"));
        subscriptionRepository.save(subscription);
        
    }
}
