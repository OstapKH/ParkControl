package es.uca.dss.ParkControl.api_http.Controllers;

import es.uca.dss.ParkControl.core.Parking.Parking;
import es.uca.dss.ParkControl.core.ParkingManagement.ParkingManagementService;
import es.uca.dss.ParkControl.core.ParkingManagement.ParkingPlanManagementService;
import es.uca.dss.ParkControl.core.ParkingManagement.ParkingStatisticsManagementService;
import es.uca.dss.ParkControl.core.ParkingManagement.ParkingSubscriptionManagementService;
import es.uca.dss.ParkControl.core.Record.Record;
import es.uca.dss.ParkControl.core.Subscription.Subscription;
import es.uca.dss.ParkControl.core.Subscription.SubscriptionType;
import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    // Method to create a parking
    @PostMapping("/parking")
    public UUID createParking(@RequestBody ParkingCreationRequest request) {
        return parkingManagementService.createParking(request.getName(), request.getMaxNumberOfSpaces(), request.getZipCode());
    }

    // Method to change parking details
    @PatchMapping("/parking/{id}")
    public void changeParkingDetails(@PathVariable UUID id, @RequestBody ParkingUpdateRequest request) {
        parkingManagementService.changeParkingDetails(id, request.getName(), request.getMaxNumberOfSpaces(), request.getZipCode());
    }

    //Method to get a parking by id
    @GetMapping("/parking/{id}")
    public Parking getParkingById(@PathVariable UUID id) {
        return parkingManagementService.getParkingById(id);
    }

    // Method to get all parkings
    @GetMapping("/parking")
    public Iterable<Parking> getAllParkings() {
        return parkingManagementService.getAllParkings();
    }

    // Method to delete a parking
    @DeleteMapping("/parking/{id}")
    public void deleteParking(@PathVariable UUID id) {
        parkingManagementService.deleteParking(id);
    }

    // Method to get all vehicles in a parking
    @GetMapping("/parking/{id}/vehicles")
    public Iterable<Vehicle> getAllCarsInParking(@PathVariable UUID id) {
        return parkingManagementService.getAllAllocatedVehiclesInParking(id);
    }

    // Method to get entries statistics by day
    @GetMapping("/parking/{id}/statistics/entries/day")
    public Optional<List<Record>> getEntriesStatisticByDay(@PathVariable UUID id, @RequestParam("day") LocalDateTime dayDate) {
        return statisticsManagementService.getEntriesStatisticByDay(id, dayDate);
    }

    // Method to get exits statistics by day
    @GetMapping("/parking/{id}/statistics/exits/day")
    public Optional<List<Record>> getExitsStatisticByDay(@PathVariable UUID id, @RequestParam("dayDate") LocalDateTime dayDate) {
        return statisticsManagementService.getExitsStatisticByDay(id, dayDate);
    }

    // Method to get entries statistics by month
    @GetMapping("/parking/{id}/statistics/entries/month")
    public Optional<List<Record>> getEntriesStatisticByMonth(@PathVariable UUID id, @RequestParam("monthDate") LocalDateTime monthDate) {
        return statisticsManagementService.getEntriesStatisticByMonth(id, monthDate);
    }

    // Method to get exits statistics by month
    @GetMapping("/parking/{id}/statistics/exits/month")
    public Optional<List<Record>> getExitsStatisticByMonth(@PathVariable UUID id, @RequestParam("monthDate") LocalDateTime monthDate) {
        return statisticsManagementService.getExitsStatisticByMonth(id, monthDate);
    }

    // Method to change the price of a subscription type
    @PutMapping("/subscription/{name}")
    public void changeSubscriptionTypePrice(@PathVariable String name, @RequestParam("newPrice") double newPrice) {
        subscriptionManagementService.changeSubscriptionTypePrice(name, newPrice);
    }

    // Method to create a subscription type
    @PostMapping("/subscription")
    public UUID createSubscriptionType(@RequestParam("name") String name, @RequestParam("price") double price) {
        return subscriptionManagementService.createSubscriptionType(name, price);
    }

    // Method to delete a subscription type
    @DeleteMapping("/subscription/{name}")
    public void deleteSubscriptionType(@PathVariable String name) {
        subscriptionManagementService.deleteSubscriptionType(name);
    }

    // Method to get all subscription types
    @GetMapping("/subscriptionTypes")
    public Iterable<SubscriptionType> getAllSubscriptionTypes() {
        return subscriptionManagementService.getAllSubscriptionTypes();
    }

    // Method to get all subscriptions
    @GetMapping("/subscriptions")
    public Iterable<Subscription> getAllSubscriptions() {
        return subscriptionManagementService.getAllSubscriptions();
    }

    // Method to create a plan
    @PostMapping("/plan")
    public void createPlan(@RequestParam("name") String name, @RequestParam("price") double price) {
        planManagementService.createPlan(name, price);
    }

    // Method to change plan price
    @PutMapping("/plan/{name}/price")
    public void changePlanPrice(@PathVariable String name, @RequestParam("newPrice") double newPrice) {
        planManagementService.changePlanPrice(name, newPrice);
    }

    // Method to change plan name
    @PutMapping("/plan/{name}/name")
    public void changePlanName(@PathVariable String name, @RequestParam("newName") String newName) {
        planManagementService.changePlanName(name, newName);
    }

    // Method to delete a plan
    @DeleteMapping("/plan/{name}")
    public void deletePlan(@PathVariable String name) {
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

