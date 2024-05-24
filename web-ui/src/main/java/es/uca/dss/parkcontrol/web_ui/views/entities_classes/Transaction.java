package es.uca.dss.parkcontrol.web_ui.views.entities_classes;

import java.time.LocalDate;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private double amountOfPayment;
    private boolean isDone;
    private LocalDate dateOfPayment;
    private UUID conceptID;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getAmountOfPayment() {
        return amountOfPayment;
    }

    public void setAmountOfPayment(double amountOfPayment) {
        this.amountOfPayment = amountOfPayment;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public UUID getConceptID() {
        return conceptID;
    }

    public void setConceptID(UUID conceptID) {
        this.conceptID = conceptID;
    }
}