package es.uca.dss.ParkControl.core.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryTicketRepository implements TicketRepository{
    private List<Ticket> tickets = new ArrayList<>();

    @Override
    public void save(Ticket ticket) {
        UUID id = ticket.getId();
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getId().equals(id)) {
                tickets.set(i, ticket);
                return;
            }
        }
        tickets.add(ticket);
    }

    @Override
    public Ticket findById(UUID id) {
        for (Ticket ticket : tickets) {
            if (ticket.getId().equals(id)) {
                return ticket;
            }
        }
        return null;
    }

    @Override
    public List<Ticket> findAll() {
        return tickets;
    }

    @Override
    public void deleteById(UUID id) {
        tickets.removeIf(ticket -> ticket.getId().equals(id));
    }

    @Override
    public List<Ticket> findByAllByRegistrationNumber(String registrationNumber) {
        List<Ticket> allTickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getVehicle().getRegistrationNumber().equals(registrationNumber)) {
                allTickets.add(ticket);
            }
        }
        return allTickets;
    }

    @Override
    public List<Ticket> findByAllByVehicleId(UUID vehicleId) {
        List<Ticket> allTickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getVehicle().getId().equals(vehicleId)) {
                allTickets.add(ticket);
            }
        }
        return allTickets;
    }

}
