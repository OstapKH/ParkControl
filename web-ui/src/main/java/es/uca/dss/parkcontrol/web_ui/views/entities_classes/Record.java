package es.uca.dss.parkcontrol.web_ui.views.entities_classes;

import java.time.LocalDateTime;
import java.util.UUID;

public class Record {
    private UUID id;

    private Ticket ticket;

    private Parking parking;

    private LocalDateTime dateOfEntry;

    private LocalDateTime dateOfExit;

    private Transaction transaction;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public LocalDateTime getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(LocalDateTime dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public LocalDateTime getDateOfExit() {
        return dateOfExit;
    }

    public void setDateOfExit(LocalDateTime dateOfExit) {
        this.dateOfExit = dateOfExit;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}