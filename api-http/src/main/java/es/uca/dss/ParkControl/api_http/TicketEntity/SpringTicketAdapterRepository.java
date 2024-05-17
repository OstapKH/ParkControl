package es.uca.dss.ParkControl.api_http.TicketEntity;

import es.uca.dss.ParkControl.core.Ticket.Ticket;
import es.uca.dss.ParkControl.core.Ticket.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

import java.util.List;
import java.util.UUID;

@Repository
public class SpringTicketAdapterRepository implements TicketRepository {
    private final SpringTicketJpaRepository springTicketJpaRepository;

    @Autowired
    public SpringTicketAdapterRepository(SpringTicketJpaRepository springTicketJpaRepository) {
        this.springTicketJpaRepository = springTicketJpaRepository;
    }

    @Override
    public void save(Ticket ticket) {
        springTicketJpaRepository.save(ticket);
    }

    @Override
    public Ticket findById(UUID id) {
        return springTicketJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Ticket> findAll() {
        return springTicketJpaRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        springTicketJpaRepository.deleteById(id);
    }

    @Override
    public List<Ticket> findByAllByRegistrationNumber(String registrationNumber) {
        List<Ticket> allTickets = springTicketJpaRepository.findAll();
        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : allTickets) {
            if (ticket.getVehicle().getRegistrationNumber().equals(registrationNumber)) {
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    @Override
    public List<Ticket> findByAllByVehicleId(UUID vehicleId) {
        List<Ticket> allTickets = springTicketJpaRepository.findAll();
        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : allTickets) {
            if (ticket.getVehicle().getId().equals(vehicleId)) {
                tickets.add(ticket);
            }
        }
        return tickets;
    }

}
