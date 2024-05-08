package es.uca.dss.ParkControl.core.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketRepository {
    void save(Ticket ticket);
    Ticket findById(UUID id);
    List<Ticket> findAll();
    void deleteById(UUID id);
    List<Ticket> findByAllByRegistrationNumber(String registrationNumber);
}
