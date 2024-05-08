package es.uca.dss.ParkControl.core.Record;

import es.uca.dss.ParkControl.core.Parking.Parking;
import es.uca.dss.ParkControl.core.Ticket.Ticket;
import es.uca.dss.ParkControl.core.Transaction.Transaction;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "records")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Ticket ticket;

    @ManyToOne
    private Parking parking;

    private LocalDateTime dateOfEntry;

    private LocalDateTime dateOfExit;

    @ManyToOne
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