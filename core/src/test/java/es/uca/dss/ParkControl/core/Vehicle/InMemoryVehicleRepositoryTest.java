package es.uca.dss.ParkControl.core.Vehicle;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
public class InMemoryVehicleRepositoryTest {
    private InMemoryVehicleRepository inMemoryVehicleRepository;
    private Vehicle vehicle;
    @Before
    public void SetUp(){
        inMemoryVehicleRepository = new InMemoryVehicleRepository();
        vehicle = new Vehicle();
        vehicle.setId(UUID.randomUUID());
        vehicle.setRegistrationNumber("Example");
    }

    @Test
    public void testSave(){
        inMemoryVehicleRepository.save(vehicle);
        List<Vehicle> vehicles = inMemoryVehicleRepository.findAll();
        assertEquals(1,vehicles.size());
        Vehicle retrievedVehicle = inMemoryVehicleRepository.findById(vehicle.getId());
        assertEquals(retrievedVehicle,vehicle);
    }

    @Test
    public void testFindById(){
        inMemoryVehicleRepository.save(vehicle);
        Vehicle retrievedVehicle = inMemoryVehicleRepository.findById(vehicle.getId());
        assertEquals(retrievedVehicle,vehicle);
    }

    @Test
    public void testFindByRegistrationNumber(){
        inMemoryVehicleRepository.save(vehicle);
        Vehicle retrievedVehicle = inMemoryVehicleRepository.findByRegistrationNumber(vehicle.getRegistrationNumber());
        assertEquals(retrievedVehicle,vehicle);
    }

    @Test
    public void testFindAll(){
        List<Vehicle> vehicles = inMemoryVehicleRepository.findAll();
        assertNotNull(vehicles);
        assertEquals(0,vehicles.size());
    }

    @Test
    public void testDeleteById(){
        inMemoryVehicleRepository.save(vehicle);
        inMemoryVehicleRepository.deleteById(vehicle.getId());
        List<Vehicle> vehicles = inMemoryVehicleRepository.findAll();
        assertEquals(0,vehicles.size());
    }
}
