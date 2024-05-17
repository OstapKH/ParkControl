package es.uca.dss.ParkControl.api_http.Controllers.RequestBodies;

import java.util.UUID;

public class TicketIdRequestBody {
    private UUID ticketId;

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }
}
