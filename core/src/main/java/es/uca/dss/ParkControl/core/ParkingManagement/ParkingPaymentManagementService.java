package es.uca.dss.ParkControl.core.ParkingManagement;

import es.uca.dss.ParkControl.core.Plan.PlanType;
import es.uca.dss.ParkControl.core.Subscription.Subscription;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionRepository;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionService;
import es.uca.dss.ParkControl.core.Ticket.Ticket;
import es.uca.dss.ParkControl.core.Ticket.TicketRepository;
import es.uca.dss.ParkControl.core.Ticket.TicketService;
import es.uca.dss.ParkControl.core.Transaction.Transaction;
import es.uca.dss.ParkControl.core.Transaction.TransactionRepository;
import es.uca.dss.ParkControl.core.Transaction.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service

public class ParkingPaymentManagementService {
    private SubscriptionService subscriptionService;

    private TransactionService transactionService;

    private TicketService ticketService;

    public ParkingPaymentManagementService(SubscriptionRepository subscriptionRepository, TransactionRepository transactionRepository, TicketRepository ticketRepository) {
        this.subscriptionService = new SubscriptionService(subscriptionRepository);
        this.transactionService = new TransactionService(transactionRepository);
        this.ticketService = new TicketService(ticketRepository);
    }

    // Method to simulate payment of a subscription by card
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
        subscriptionService.createSubscription(subscription);
    }

    // Method to simulate payment of a subscription by cash
    public double paymentOfSubscriptionByCash(UUID subscriptionId, double amount) {
        Subscription subscription = subscriptionService.getSubscription(subscriptionId);
        double subscriptionPrice = subscription.getSubscriptionType().getPrice();
        // If user pays less than the ticket price, we return the change
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
        subscriptionService.createSubscription(subscription);
        // If user pays more than the ticket price, we return the change
        return amount - subscriptionPrice;
    }

    // Method that simulates payment by card
    public void paymentOfTicketByCard(UUID ticketId) {
        Ticket ticket = ticketService.getTicket(ticketId);
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAmountOfPayment(getTicketPrice(ticketId));
        transaction.setConceptID(ticketId);
        // We suppose that payment is successful and done by card
        transaction.setDone(true);
        ticket.setDateOfPayment(LocalDateTime.now());
        ticketService.createTicket(ticket);
        transactionService.createTransaction(transaction);
    }

    // Method that simulates payment by cash
    public double paymentOfTicketByCash(UUID ticketId, double amount) {
        Ticket ticket = ticketService.getTicket(ticketId);
        double ticketPrice = getTicketPrice(ticketId);
        // If user pays less than the ticket price, we return negative value
        if (amount < ticketPrice) {
            return amount - ticketPrice;
        }
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAmountOfPayment(amount);
        transaction.setConceptID(ticketId);
        transaction.setDone(true);
        ticket.setDateOfPayment(LocalDateTime.now());
        // If user pays more than the ticket price, we return the change
        transactionService.createTransaction(transaction);
        ticketService.createTicket(ticket);
        return amount - ticketPrice;
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
            return days * ticketPlanPrice + (hours - days * 24) * ticketPlanPrice / 24.0;
        } else if (ticketPlanType == PlanType.HOURS) {
            return hours * ticketPlanPrice + minutes * ticketPlanPrice / 60.0;
        } else return minutes * ticketPlanPrice;
    }

    // Method to calculate the plan type of a ticket
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
}
