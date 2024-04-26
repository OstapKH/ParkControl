package es.uca.dss.ParkControl.core.UsageExample;

import es.uca.dss.ParkControl.core.Parking.InMemoryParkingRepository;
import es.uca.dss.ParkControl.core.Parking.Parking;
import es.uca.dss.ParkControl.core.Plan.InMemoryPlanRepository;
import es.uca.dss.ParkControl.core.Plan.Plan;
import es.uca.dss.ParkControl.core.Plan.PlanType;
import es.uca.dss.ParkControl.core.Record.InMemoryRecordRepository;
import es.uca.dss.ParkControl.core.Record.Record;
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
import java.time.LocalDateTime;
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


        //Creating a parking and adding it to the repository
        Parking parking = new Parking();
        parking.setId(UUID.randomUUID());
        parking.setName("Parking ESI");
        parking.setMaxNumberOfSpaces(200);
        parking.setZipCode("11123");
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

        List<Vehicle> allocatedVehicles = new ArrayList<>();
        // Generating 25 random vehicles and adding them to the list of allocated vehicles
        // As well we generate 25 tickets for each vehicle
        for (int i = 0; i < 25; i++) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(UUID.randomUUID());
            vehicle.setRegistrationNumber("12"+ String.valueOf(i) + "4ABC");
            Ticket ticket = new Ticket();
            ticket.setId(UUID.randomUUID());
            ticket.setParking(parking);
            ticket.setVehicle(vehicle);
            ticket.setDateOfIssue(LocalDateTime.now().plusDays(i));
            ticket.setPlan(planDay);
            ticketRepository.save(ticket);
//            LocalDateTime dateOfEntry = LocalDateTime.now();
//            dateOfEntry = dateOfEntry.plusMinutes(i);
            vehicleRepository.save(vehicle);
            allocatedVehicles.add(vehicle);
        }

        parking.setAllocatedVehicles(allocatedVehicles);


        // Creating a subcription and adding it to the repository
        Subscription subscription = new Subscription();
        subscription.setId(UUID.randomUUID());
        // We suppose that car with "1214ABC" registration number is a subscriber of MONTH type
        subscription.setVehicle(vehicleRepository.findByRegistrationNumber("1214ABC"));
//        subscription.setSubscriptionType(SubscriptionType.MONTH);
        subscription.setDateOfPurchase(LocalDateTime.parse("2023-02-01"));
        subscriptionRepository.save(subscription);

        // User would like to pay for the ticket
        // Machine scans QR and finds the ticket
        Ticket ticket = ticketRepository.findAll().get(0);
        // User pays for the ticket
        Transaction transaction = new Transaction();
        transaction.setConceptID(ticket.getId());
        // TODO  Calculation of amount to pay
        double amountToPay = ticket.getPlan().getPrice();
        transaction.setAmountOfPayment(amountToPay);
        transaction.setDateOfPayment(LocalDate.now());
        // We suppose that user pays for the ticket
        transaction.setDone(true);
        transactionRepository.save(transaction);

        if (transaction.isDone()){
            ticket.setDateOfPayment(LocalDateTime.now());
            ticketRepository.save(ticket);
            // Show message that ticket was paid successfully
        }

        //User goes to barrier
        //System scans reg. number or ticket and checks if such ticket was paid
        //Opens barrier

    }
}
