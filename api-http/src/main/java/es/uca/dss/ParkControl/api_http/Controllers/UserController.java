package es.uca.dss.ParkControl.api_http.Controllers;

import es.uca.dss.ParkControl.api_http.Controllers.RequestBodies.TicketIdRequestBody;
import es.uca.dss.ParkControl.api_http.Controllers.RequestBodies.VehicleRegistrationNumberRequestBody;
import es.uca.dss.ParkControl.core.ParkingManagement.*;
import es.uca.dss.ParkControl.core.Subscription.Subscription;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionType;
import es.uca.dss.ParkControl.core.Ticket.Ticket;
import es.uca.dss.ParkControl.core.Vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private VehicleService vehicleService;

    // Method to get a ticket when car enters
    @PostMapping("/parking/{parkingId}/vehicles/enters")
    public Ticket addVehicleToParking(@PathVariable UUID parkingId, @RequestBody(required = false) VehicleRegistrationNumberRequestBody vehicleRegistrationNumberRequestBody) {
        if (vehicleRegistrationNumberRequestBody != null) {
            Optional<Ticket> ticket = parkingEntranceAndExitManagementService.addVehicleToParking(parkingId, vehicleRegistrationNumberRequestBody.getRegistrationNumber());
            return ticket.orElse(null);
        } else {
            Optional<Ticket> ticket = parkingEntranceAndExitManagementService.addVehicleToParking(parkingId);
            return ticket.orElse(null);
        }
    }

    // Method to pay ticket by card
    @PostMapping("/parking/{parkingId}/payment/card/{ticketId}")
    public void paymentOfTicketByCard(@PathVariable UUID ticketId) {
        parkingPaymentManagementService.paymentOfTicketByCard(ticketId);
    }

    // Method to pay ticket by cash
    @PostMapping("/parking/{parkingId}/payment/cash/{ticketId}")
    public double paymentOfTicketByCash(@PathVariable UUID ticketId, @RequestParam double amount) {
        return parkingPaymentManagementService.paymentOfTicketByCash(ticketId, amount);
    }

    // Method to get ticket by id
    @GetMapping("/ticket/{ticketId}")
    public Ticket getTicketById(@PathVariable UUID ticketId) {
        return parkingTicketManagementService.getTicketById(ticketId);
    }

    // Method to exit parking
    @DeleteMapping("/parking/{parkingId}/vehicles/exits")
    public boolean exitParking(@PathVariable UUID parkingId, @RequestBody(required = false) VehicleRegistrationNumberRequestBody vehicleRegistrationNumberRequestBody, @RequestBody(required = false) TicketIdRequestBody ticketIdRequestBody) {
        if (vehicleRegistrationNumberRequestBody != null) {
            return parkingEntranceAndExitManagementService.vehicleExit(parkingId, vehicleRegistrationNumberRequestBody.getRegistrationNumber());
        } else {
            return parkingEntranceAndExitManagementService.vehicleExit(parkingId, parkingTicketManagementService.getTicketById(ticketIdRequestBody.getTicketId()));
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

    // Method to create subscription type
    @PostMapping("/subscription/create")
    public UUID createSubscriptionType(@RequestBody SubscriptionTypeCreateRequest request) {
        return parkingSubscriptionManagementService.createSubscriptionType(request.getName(), request.getPrice());
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
