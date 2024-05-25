package es.uca.dss.ParkControl.core.ParkingManagement;

import es.uca.dss.ParkControl.core.Plan.Plan;
import es.uca.dss.ParkControl.core.Plan.PlanRepository;
import es.uca.dss.ParkControl.core.Plan.PlanService;
import es.uca.dss.ParkControl.core.Plan.PlanType;
import es.uca.dss.ParkControl.core.QRCodeGeneration.QRCodeGenerator;
import es.uca.dss.ParkControl.core.Ticket.Ticket;
import es.uca.dss.ParkControl.core.Ticket.TicketRepository;
import es.uca.dss.ParkControl.core.Ticket.TicketService;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Service for managing parking tickets.
 */
@Service
public class ParkingTicketManagementService {

    private PlanService planService;
    private TicketService ticketService;

    /**
     * Constructor for the ParkingTicketManagementService.
     *
     * @param ticketRepository the ticket repository
     * @param planRepository   the plan repository
     */
    public ParkingTicketManagementService(TicketRepository ticketRepository, PlanRepository planRepository) {
        this.ticketService = new TicketService(ticketRepository);
        this.planService = new PlanService(planRepository);
    }

    /**
     * Method to get the price of a ticket.
     *
     * @param ticketId the id of the ticket
     * @return the price of the ticket
     */
    public double getTicketPrice(UUID ticketId) {
        Ticket ticket = ticketService.getTicket(ticketId);
        // Based on the time the vehicle has been parked, we set the plan type
        ticket.getPlan().setPlanType(calculateTicketPlanType(ticket));
        planService.createPlan(ticket.getPlan());
        ticket.setPlan(ticket.getPlan());
        ticketService.createTicket(ticket);
        LocalDateTime dateOfEntry = ticket.getDateOfIssue();
        LocalDateTime tempDateTime = LocalDateTime.from(dateOfEntry);
        double ticketPlanPrice = ticket.getPlan().getPrice();
        PlanType ticketPlanType = ticket.getPlan().getPlanType();
        int weeks = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.WEEKS);
        int days = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.DAYS);
        int hours = (int) tempDateTime.until(LocalDateTime.now(), ChronoUnit.HOURS);
        long minutes = tempDateTime.until(LocalDateTime.now(), ChronoUnit.MINUTES);
        if (ticketPlanType == PlanType.WEEKS) {
            return (weeks * ticketPlanPrice * 100) + days * (ticketPlanPrice * 40 / 7.0) + ((hours - (days * 24)) * ticketPlanPrice);
        } else if (ticketPlanType == PlanType.DAYS) {
            return (days * ticketPlanPrice * 40) + (hours - days * 24) * ticketPlanPrice / 24.0;
        } else if (ticketPlanType == PlanType.HOURS) {
            return (hours * ticketPlanPrice * 5) + minutes * ticketPlanPrice / 60.0;
        } else return minutes * ticketPlanPrice;
    }

    /**
     * Method to change the plan of a ticket.
     *
     * @param ticketId the id of the ticket
     * @param newPlan  the new plan for the ticket
     */
    public void changeTicketPlan(UUID ticketId, Plan newPlan) {
        Ticket ticket = ticketService.getTicket(ticketId);
        ticket.setPlan(newPlan);
    }

    /**
     * Method to get the QR code of a ticket.
     *
     * @param ticketId the id of the ticket
     * @return the QR code of the ticket
     * @throws Exception if an error occurs while generating the QR code
     */
    public ByteArrayOutputStream getTicketQRCode(UUID ticketId) throws Exception {
        return QRCodeGenerator.generateQRCodeByteOutput(ticketId);
    }

    /**
     * Method to calculate the plan type of a ticket.
     *
     * @param ticket the ticket
     * @return the plan type of the ticket
     */
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

    /**
     * Method to get a ticket by id.
     *
     * @param ticketId the id of the ticket
     * @return the ticket
     */
    public Ticket getTicketById(UUID ticketId) {
        return ticketService.getTicket(ticketId);
    }
}
