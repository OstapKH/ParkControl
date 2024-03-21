package es.uca.dss.ParkControl.core.Vehicle;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
public class VehicleServiceTest {
    private VehicleService vehicleService;
    private VehicleRepository vehicleRepository;
    private Vehicle vehicle;
    
    @Before
    public void SetUp(){
        vehicleRepository = new InMemoryVehicleRepository();
        vehicleService = new VehicleService(vehicleRepository);

        vehicle = new Vehicle();
        vehicle.setId(UUID.randomUUID());
        vehicle.setRegistrationNumber("Example");
    }


    @Test
    public void testCreateVehicle(){
        vehicleService.createVehicle(vehicle);
        List<Vehicle> vehicles = vehicleRepository.findAll();
        assertEquals(1,vehicles.size());

        Vehicle retrievedVehicle = vehicleRepository.findById(vehicle.getId());
        assertEquals(retrievedVehicle,vehicle);
    }

    @Test
    public void testGetVehicle(){
        vehicleService.createVehicle(vehicle);
        Vehicle retrievedVehicle = vehicleService.getVehicle(vehicle.getId());
        assertEquals(retrievedVehicle,vehicle);
    }

    @Test
    public void testGetAllVehicles(){
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        assertNotNull(vehicles); //Entendemos null como no crear la lista
        assertEquals(0,vehicles.size());
    }

    @Test
    public void testDeleteVehicle(){
        vehicleService.createVehicle(vehicle);
        vehicleService.deleteVehicle(vehicle.getId());
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        assertEquals(0,vehicles.size());
    }

    @Test
    public void testGetVehicleByRegistrationNumber(){
        vehicleService.createVehicle(vehicle);
        Vehicle retrievedVehicle = vehicleService.getVehicleByRegistrationNumber(vehicle.getRegistrationNumber());
        assertEquals(retrievedVehicle,vehicle);
    }
}
