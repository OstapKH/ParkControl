package es.uca.dss.ParkControl.api_http.Controllers;

import es.uca.dss.ParkControl.core.ParkingManagement.ParkingManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/managers")
public class ManagerController {
    @Autowired
    private ParkingManagementService parkingManagementService;

    @PostMapping("/parking")
    public UUID createParking(@RequestBody ParkingCreationRequest request) {
        return parkingManagementService.createParking(request.getName(), request.getMaxNumberOfSpaces(), request.getZipCode());
    }

    @PutMapping("/parking/{id}")
    public void changeParkingDetails(@PathVariable UUID id, @RequestBody ParkingUpdateRequest request) {
        parkingManagementService.changeParkingDetails(id, request.getName(), request.getMaxNumberOfSpaces(), request.getZipCode());
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

