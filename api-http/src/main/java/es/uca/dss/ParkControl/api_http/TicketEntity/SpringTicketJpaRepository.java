package es.uca.dss.ParkControl.api_http.TicketEntity;

import es.uca.dss.ParkControl.core.Ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface SpringTicketJpaRepository extends JpaRepository<Ticket, UUID> {
}
