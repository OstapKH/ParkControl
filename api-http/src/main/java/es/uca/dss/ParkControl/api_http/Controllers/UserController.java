package es.uca.dss.ParkControl.api_http.Controllers;

import es.uca.dss.ParkControl.api_http.Controllers.RequestBodies.TicketIdRequestBody;
import es.uca.dss.ParkControl.api_http.Controllers.RequestBodies.VehicleEnterRequestBody;
import es.uca.dss.ParkControl.api_http.Controllers.RequestBodies.VehicleExitRequestBody;
import es.uca.dss.ParkControl.core.ParkingManagement.*;
import es.uca.dss.ParkControl.core.Subscription.Subscription;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionType;
import es.uca.dss.ParkControl.core.Ticket.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private ParkingEntranceAndExitManagementService parkingEntranceAndExitManagementService;
    @Autowired
    private ParkingPaymentManagementService parkingPaymentManagementService;
    @Autowired
    private ParkingTicketManagementService parkingTicketManagementService;
    @Autowired
    private ParkingSubscriptionManagementService parkingSubscriptionManagementService;

    // Method to get a ticket when car enters
    @PostMapping("/parking/vehicles/enters")
    public Ticket addVehicleToParking(@RequestBody(required = true) VehicleEnterRequestBody vehicleEnterRequestBody) {
        if (vehicleEnterRequestBody.getRegistrationNumber() != null) {
            Optional<Ticket> ticket = parkingEntranceAndExitManagementService.addVehicleToParking(vehicleEnterRequestBody.getParkingId(), vehicleEnterRequestBody.getRegistrationNumber());
            return ticket.orElse(null);
        } else {
            Optional<Ticket> ticket = parkingEntranceAndExitManagementService.addVehicleToParking(vehicleEnterRequestBody.getParkingId());
            return ticket.orElse(null);
        }
    }

    // Method to pay ticket by card
    @PostMapping("/parking/payment/card/{ticketId}")
    public void paymentOfTicketByCard(@PathVariable UUID ticketId) {
        parkingPaymentManagementService.paymentOfTicketByCard(ticketId);
    }

    // Method to pay ticket by cash
    @PostMapping("/parking/payment/cash/{ticketId}")
    public double paymentOfTicketByCash(@PathVariable UUID ticketId, @RequestParam double amount) {
        return parkingPaymentManagementService.paymentOfTicketByCash(ticketId, amount);
    }

    // Method to get ticket by id
    @GetMapping("/ticket/{ticketId}")
    public Ticket getTicketById(@PathVariable UUID ticketId) {
        return parkingTicketManagementService.getTicketById(ticketId);
    }

    // Method to exit parking
    @PostMapping("/parking/{parkingId}/vehicles/exits")
    public boolean exitParking(@PathVariable UUID parkingId, @RequestParam UUID ticketId, @RequestParam(required = false) String registrationNumber) {
        if (registrationNumber != null) {
            return parkingEntranceAndExitManagementService.vehicleExit(parkingId, registrationNumber);
        } else {
            return parkingEntranceAndExitManagementService.vehicleExit(parkingId, ticketId);
        }
    }

    // Method to get price of ticket
    @GetMapping("/ticket/{ticketId}/price")
    public double getTicketPrice(@PathVariable UUID ticketId) {
        return parkingTicketManagementService.getTicketPrice(ticketId);
    }

    // Method to get list of all available subscriptions plans
    @GetMapping("/subscriptions")
    public List<SubscriptionType> getAllSubscriptionTypes() {
        return parkingSubscriptionManagementService.getAllSubscriptionTypes();
    }


    // Method to create subscription for a vehicle
    @PostMapping("/subscription/subscribe")
    public Subscription createSubscription(@RequestParam String registrationNumber, @RequestParam String subscriptionTypeName) {
        SubscriptionType subscriptionType = parkingSubscriptionManagementService.getSubscriptionTypeByName(subscriptionTypeName);
        if (subscriptionType == null) {
            return null;
        }
        return parkingSubscriptionManagementService.subscribeVehicle(registrationNumber, subscriptionType);
    }

    // Method to pay for subscription by card
    @PostMapping("/subscription/{subscriptionId}/payment/card")
    public void paymentOfSubscriptionByCard(@PathVariable UUID subscriptionId) {
        parkingPaymentManagementService.paymentOfSubscriptionByCard(subscriptionId);
    }

    // Method to pay for subscription by cash
    @PostMapping("/subscription/{subscriptionId}/payment/cash")
    public double paymentOfSubscriptionByCash(@PathVariable UUID subscriptionId, @RequestParam double amount) {
        return parkingPaymentManagementService.paymentOfSubscriptionByCash(subscriptionId, amount);
    }

    public static class SubscriptionTypeCreateRequest {
        private String name;
        private double price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

}
