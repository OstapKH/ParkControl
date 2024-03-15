package es.uca.dss.ParkControl.core.Ticket;

import java.util.List;
import java.util.UUID;

public class TicketService {
    private TicketRepository repository;

    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public void createTicket(Ticket ticket) {
        repository.save(ticket);
    }

    public Ticket getTicket(UUID id) {
        return repository.findById(id);
    }

    public List<Ticket> getAllTickets() {
        return repository.findAll();
    }

    public void deleteTicket(UUID id) {
        repository.deleteById(id);
    }
}
