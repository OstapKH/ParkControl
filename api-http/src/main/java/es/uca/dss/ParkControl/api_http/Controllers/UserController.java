package es.uca.dss.ParkControl.api_http.Controllers;

import es.uca.dss.ParkControl.core.ParkingManagement.ParkingEntranceAndExitManagementService;
import es.uca.dss.ParkControl.core.ParkingManagement.ParkingManagementService;
import es.uca.dss.ParkControl.core.ParkingManagement.ParkingPaymentManagementService;
import es.uca.dss.ParkControl.core.ParkingManagement.ParkingTicketManagementService;
import es.uca.dss.ParkControl.core.Ticket.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private ParkingEntranceAndExitManagementService parkingEntranceAndExitManagementService;

    @Autowired
    private ParkingPaymentManagementService parkingPaymentManagementService;

    @PostMapping("/parking/{parkingId}/vehicles")
    public Ticket addVehicleToParking(@PathVariable UUID parkingId, @RequestParam String registrationNumber) {
        Optional<Ticket> ticket = parkingEntranceAndExitManagementService.addVehicleToParking(parkingId, registrationNumber);
        return ticket.orElse(null);
    }

//    @PostMapping("/parking/{parkingId}/vehicles")
//    public Ticket addVehicleToParking(@PathVariable UUID parkingId) {
//        Optional<Ticket> ticket = parkingManagementService.addVehicleToParking(parkingId);
//        return ticket.orElse(null);
//    }

    @PostMapping("/parking/{parkingId}/payment/card/{ticketId}")
    public void paymentOfTicketByCard(@PathVariable UUID ticketId) {
        parkingPaymentManagementService.paymentOfTicketByCard(ticketId);
    }

    @PostMapping("/parking/{parkingId}/payment/cash/{ticketId}")
    public double paymentOfTicketByCash(@PathVariable UUID ticketId, @RequestParam double amount) {
        return parkingPaymentManagementService.paymentOfTicketByCash(ticketId, amount);
    }


}
