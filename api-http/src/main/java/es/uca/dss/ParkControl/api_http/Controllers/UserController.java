package es.uca.dss.ParkControl.api_http.Controllers;

import es.uca.dss.ParkControl.api_http.Controllers.RequestBodies.VehicleEnterRequestBody;
import es.uca.dss.ParkControl.core.ParkingManagement.*;
import es.uca.dss.ParkControl.core.Subscription.Subscription;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionType;
import es.uca.dss.ParkControl.core.Ticket.Ticket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Add a vehicle to parking and get a ticket")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehicle added successfully and ticket generated"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/parking/vehicles/enters")
    public Ticket addVehicleToParking(@Parameter(description = "Request body for vehicle entering parking", required = true) @RequestBody VehicleEnterRequestBody vehicleEnterRequestBody) {
        if (vehicleEnterRequestBody.getRegistrationNumber() != null) {
            Optional<Ticket> ticket = parkingEntranceAndExitManagementService.addVehicleToParking(vehicleEnterRequestBody.getParkingId(), vehicleEnterRequestBody.getRegistrationNumber());
            return ticket.orElse(null);
        } else {
            Optional<Ticket> ticket = parkingEntranceAndExitManagementService.addVehicleToParking(vehicleEnterRequestBody.getParkingId());
            return ticket.orElse(null);
        }
    }

    @Operation(summary = "Pay for a ticket by card")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment successful"),
        @ApiResponse(responseCode = "400", description = "Invalid ticket ID supplied"),
        @ApiResponse(responseCode = "404", description = "Ticket not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/parking/payment/card/{ticketId}")
    public void paymentOfTicketByCard(@Parameter(description = "ID of the ticket to pay for", required = true) @PathVariable UUID ticketId) {
        parkingPaymentManagementService.paymentOfTicketByCard(ticketId);
    }

    @Operation(summary = "Pay for a ticket by cash")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment successful and change returned"),
        @ApiResponse(responseCode = "400", description = "Invalid ticket ID or amount supplied"),
        @ApiResponse(responseCode = "404", description = "Ticket not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/parking/payment/cash/{ticketId}")
    public double paymentOfTicketByCash(@Parameter(description = "ID of the ticket to pay for", required = true) @PathVariable UUID ticketId, @Parameter(description = "Amount of cash given", required = true) @RequestParam double amount) {
        return parkingPaymentManagementService.paymentOfTicketByCash(ticketId, amount);
    }

    @Operation(summary = "Get a ticket by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket found"),
        @ApiResponse(responseCode = "400", description = "Invalid ticket ID supplied"),
        @ApiResponse(responseCode = "404", description = "Ticket not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/ticket/{ticketId}")
    public Ticket getTicketById(@Parameter(description = "ID of the ticket to retrieve", required = true) @PathVariable UUID ticketId) {
        return parkingTicketManagementService.getTicketById(ticketId);
    }

    @Operation(summary = "Exit a parking")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exit successful"),
        @ApiResponse(responseCode = "400", description = "Invalid parking ID, ticket ID or registration number supplied"),
        @ApiResponse(responseCode = "404", description = "Parking or ticket not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/parking/{parkingId}/vehicles/exits")
    public boolean exitParking(@Parameter(description = "ID of the parking to exit", required = true) @PathVariable UUID parkingId, @Parameter(description = "ID of the ticket", required = true) @RequestParam UUID ticketId, @Parameter(description = "Registration number of the vehicle (optional)", required = false) @RequestParam(required = false) String registrationNumber) {
        if (registrationNumber != null) {
            return parkingEntranceAndExitManagementService.vehicleExit(parkingId, registrationNumber);
        } else {
            return parkingEntranceAndExitManagementService.vehicleExit(parkingId, ticketId);
        }
    }

    @Operation(summary = "Get the price of a ticket")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Price retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid ticket ID supplied"),
        @ApiResponse(responseCode = "404", description = "Ticket not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/ticket/{ticketId}/price")
    public double getTicketPrice(@Parameter(description = "ID of the ticket to get the price for", required = true) @PathVariable UUID ticketId) {
        return parkingTicketManagementService.getTicketPrice(ticketId);
    }

    @Operation(summary = "Get all available subscription types")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscription types retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/subscriptions")
    public List<SubscriptionType> getAllSubscriptionTypes() {
        return parkingSubscriptionManagementService.getAllSubscriptionTypes();
    }

    @Operation(summary = "Create a subscription for a vehicle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscription created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid registration number or subscription type name supplied"),
        @ApiResponse(responseCode = "404", description = "Subscription type not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/subscription/subscribe")
    public Subscription createSubscription(@Parameter(description = "Registration number of the vehicle", required = true) @RequestParam String registrationNumber, @Parameter(description = "Name of the subscription type", required = true) @RequestParam String subscriptionTypeName) {
        SubscriptionType subscriptionType = parkingSubscriptionManagementService.getSubscriptionTypeByName(subscriptionTypeName);
        if (subscriptionType == null) {
            return null;
        }
        return parkingSubscriptionManagementService.subscribeVehicle(registrationNumber, subscriptionType);
    }

    @Operation(summary = "Pay for a subscription by card")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment successful"),
        @ApiResponse(responseCode = "400", description = "Invalid subscription ID supplied"),
        @ApiResponse(responseCode = "404", description = "Subscription not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/subscription/{subscriptionId}/payment/card")
    public void paymentOfSubscriptionByCard(@Parameter(description = "ID of the subscription to pay for", required = true) @PathVariable UUID subscriptionId) {
        parkingPaymentManagementService.paymentOfSubscriptionByCard(subscriptionId);
    }

    @Operation(summary = "Pay for a subscription by cash")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment successful and change returned"),
        @ApiResponse(responseCode = "400", description = "Invalid subscription ID or amount supplied"),
        @ApiResponse(responseCode = "404", description = "Subscription not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/subscription/{subscriptionId}/payment/cash")
    public double paymentOfSubscriptionByCash(@Parameter(description = "ID of the subscription to pay for", required = true) @PathVariable UUID subscriptionId, @Parameter(description = "Amount of cash given", required = true) @RequestParam double amount) {
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