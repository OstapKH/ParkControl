package es.uca.dss.ParkControl.api_http.Controllers.RequestBodies;

import java.util.UUID;

public class TicketIdRequestBody {
    private String ticketId;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}
