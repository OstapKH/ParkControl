package es.uca.dss.ParkControl.api_http.Controllers;

import es.uca.dss.ParkControl.core.Parking.Parking;
import es.uca.dss.ParkControl.core.ParkingManagement.ParkingManagementService;
import es.uca.dss.ParkControl.core.ParkingManagement.ParkingPlanManagementService;
import es.uca.dss.ParkControl.core.ParkingManagement.ParkingStatisticsManagementService;
import es.uca.dss.ParkControl.core.ParkingManagement.ParkingSubscriptionManagementService;
import es.uca.dss.ParkControl.core.Plan.Plan;
import es.uca.dss.ParkControl.core.Record.Record;
import es.uca.dss.ParkControl.core.Subscription.Subscription;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionType;
import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/managers")
public class ManagerController {
    @Autowired
    private ParkingManagementService parkingManagementService;

    @Autowired
    private ParkingStatisticsManagementService statisticsManagementService;

    @Autowired
    private ParkingSubscriptionManagementService subscriptionManagementService;

    @Autowired
    private ParkingPlanManagementService planManagementService;

    @Operation(summary = "Create a new parking")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Parking created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/parking")
    public UUID createParking(@Parameter(description = "Request body for creating a new parking", required = true) @RequestBody ParkingCreationRequest request) {
        return parkingManagementService.createParking(request.getName(), request.getMaxNumberOfSpaces(), request.getZipCode());
    }

    @Operation(summary = "Update parking details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Parking details updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "404", description = "Parking not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PatchMapping("/parking/{id}")
    public void changeParkingDetails(@Parameter(description = "ID of the parking to update", required = true) @PathVariable UUID id, @RequestBody ParkingUpdateRequest request) {
        parkingManagementService.changeParkingDetails(id, request.getName(), request.getMaxNumberOfSpaces(), request.getZipCode());
    }

    @Operation(summary = "Get parking by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the parking"),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
        @ApiResponse(responseCode = "404", description = "Parking not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/parking/{id}")
    public Parking getParkingById(@Parameter(description = "ID of the parking to retrieve", required = true) @PathVariable UUID id) {
        return parkingManagementService.getParkingById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking not found"));
    }

    @Operation(summary = "Get all parkings")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all parkings"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/parking")
    public Iterable<Parking> getAllParkings() {
        return parkingManagementService.getAllParkings();
    }

    @Operation(summary = "Delete a parking")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Parking deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
        @ApiResponse(responseCode = "404", description = "Parking not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @DeleteMapping("/parking/{id}")
    public void deleteParking(@Parameter(description = "ID of the parking to delete", required = true) @PathVariable UUID id) {
        parkingManagementService.deleteParking(id);
    }

    @Operation(summary = "Get all vehicles in a parking")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all vehicles in the parking"),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
        @ApiResponse(responseCode = "404", description = "Parking not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/parking/{id}/vehicles")
    public Iterable<Vehicle> getAllCarsInParking(@Parameter(description = "ID of the parking", required = true) @PathVariable UUID id) {
        return parkingManagementService.getAllAllocatedVehiclesInParking(id);
    }

    @Operation(summary = "Get entries statistics by day or month")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found entries statistics"),
        @ApiResponse(responseCode = "400", description = "Invalid id or date supplied"),
        @ApiResponse(responseCode = "404", description = "Parking not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/parking/{id}/statistics/entries")
    public Optional<List<Record>> getEntriesStatisticByDayOrMonth(@Parameter(description = "ID of the parking", required = true) @PathVariable UUID id, @RequestParam(value = "dayDate", required = false) LocalDateTime dayDate, @RequestParam(value = "monthDate", required = false) LocalDateTime monthDate) {
        if (dayDate != null) {
            return statisticsManagementService.getEntriesStatisticByDay(id, dayDate);
        } else if (monthDate != null) {
            return statisticsManagementService.getEntriesStatisticByMonth(id, monthDate);
        } else return Optional.empty();
    }

    @Operation(summary = "Get exits statistics by day or month")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found exits statistics"),
        @ApiResponse(responseCode = "400", description = "Invalid id or date supplied"),
        @ApiResponse(responseCode = "404", description = "Parking not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/parking/{id}/statistics/exits")
    public Optional<List<Record>> getExitsStatisticByDayOrMonth(@Parameter(description = "ID of the parking", required = true) @PathVariable UUID id, @RequestParam(value = "dayDate", required = false) LocalDateTime dayDate, @RequestParam(value = "monthDate", required = false) LocalDateTime monthDate) {
        if (dayDate != null) {
            return statisticsManagementService.getExitsStatisticByDay(id, dayDate);
        } else if (monthDate != null) {
            return statisticsManagementService.getExitsStatisticByMonth(id, monthDate);
        } else return Optional.empty();
    }

    @Operation(summary = "Change the price of a subscription type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscription type price changed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid name or price supplied"),
        @ApiResponse(responseCode = "404", description = "Subscription type not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PatchMapping("/subscription/{name}")
    public void changeSubscriptionTypePrice(@Parameter(description = "Name of the subscription type", required = true) @PathVariable String name, @Parameter(description = "New price of the subscription type", required = true) @RequestParam("newPrice") double newPrice) {
        subscriptionManagementService.changeSubscriptionTypePrice(name, newPrice);
    }

    @Operation(summary = "Create a new subscription type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscription type created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid name or price supplied"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/subscription")
    public UUID createSubscriptionType(@Parameter(description = "Name of the subscription type", required = true) @RequestParam("name") String name, @Parameter(description = "Price of the subscription type", required = true) @RequestParam("price") double price) {
        return subscriptionManagementService.createSubscriptionType(name, price);
    }

    @Operation(summary = "Delete a subscription type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscription type deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid name supplied"),
        @ApiResponse(responseCode = "404", description = "Subscription type not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @DeleteMapping("/subscription/{name}")
    public void deleteSubscriptionType(@Parameter(description = "Name of the subscription type to delete", required = true) @PathVariable String name) {
        subscriptionManagementService.deleteSubscriptionType(name);
    }

    @Operation(summary = "Get all subscription types")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all subscription types"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/subscriptionTypes")
    public Iterable<SubscriptionType> getAllSubscriptionTypes() {
        return subscriptionManagementService.getAllSubscriptionTypes();
    }

    @Operation(summary = "Get all subscriptions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all subscriptions"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/subscriptions")
    public Iterable<Subscription> getAllSubscriptions() {
        return subscriptionManagementService.getAllSubscriptions();
    }

    @Operation(summary = "Get all plans")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all plans"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/plans")
    public Iterable<Plan> getAllPlans() {
        return planManagementService.getAllPlans();
    }

    @Operation(summary = "Create a new plan")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plan created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid name, price or plan type supplied"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/plan")
    public void createPlan(@Parameter(description = "Name of the plan", required = true) @RequestParam("name") String name, @Parameter(description = "Price of the plan", required = true) @RequestParam("price") double price, @Parameter(description = "Type of the plan", required = true) @RequestParam("planType") String planType) {
        planManagementService.createPlan(name, price, planType);
    }

    @Operation(summary = "Change the price of a plan")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plan price changed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid name or price supplied"),
        @ApiResponse(responseCode = "404", description = "Plan not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PutMapping("/plan/{name}/price")
    public void changePlanPrice(@Parameter(description = "Name of the plan", required = true) @PathVariable String name, @Parameter(description = "New price of the plan", required = true) @RequestParam("newPrice") double newPrice) {
        planManagementService.changePlanPrice(name, newPrice);
    }

    @Operation(summary = "Change the name of a plan")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plan name changed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid name supplied"),
        @ApiResponse(responseCode = "404", description = "Plan not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PutMapping("/plan/{name}/name")
    public void changePlanName(@Parameter(description = "Current name of the plan", required = true) @PathVariable String name, @Parameter(description = "New name of the plan", required = true) @RequestParam("newName") String newName) {
        planManagementService.changePlanName(name, newName);
    }

    @Operation(summary = "Delete a plan")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plan deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid name supplied"),
        @ApiResponse(responseCode = "404", description = "Plan not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    @DeleteMapping("/plan/{name}")
    public void deletePlan(@Parameter(description = "Name of the plan to delete", required = true) @PathVariable String name) {
        planManagementService.deletePlan(name);
    }

    public static class ParkingCreationRequest {
        private String name;
        private int maxNumberOfSpaces;
        private String zipCode;

        public ParkingCreationRequest() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMaxNumberOfSpaces() {
            return maxNumberOfSpaces;
        }

        public void setMaxNumberOfSpaces(int maxNumberOfSpaces) {
            this.maxNumberOfSpaces = maxNumberOfSpaces;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }

    public static class ParkingUpdateRequest {
        private String name;
        private int maxNumberOfSpaces;
        private String zipCode;

        public ParkingUpdateRequest() {
        }

        public ParkingUpdateRequest(String zipCode, String name, int maxNumberOfSpaces) {
            this.zipCode = zipCode;
            this.name = name;
            this.maxNumberOfSpaces = maxNumberOfSpaces;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMaxNumberOfSpaces() {
            return maxNumberOfSpaces;
        }

        public void setMaxNumberOfSpaces(int maxNumberOfSpaces) {
            this.maxNumberOfSpaces = maxNumberOfSpaces;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }

}

