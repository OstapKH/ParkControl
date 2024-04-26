package es.uca.dss.ParkControl.core.Parking;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class InMemoryParkingRepositoryTest {

    private InMemoryParkingRepository inMemoryParkingRepository;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Parking parking;
    @Before
    public void SetUp(){
      inMemoryParkingRepository = new InMemoryParkingRepository();

        parking = new Parking();
        parking.setId(UUID.randomUUID());
        parking.setName("Example");
        parking.setZipCode("12345");
        parking.setMaxNumberOfSpaces(100);
        parking.setCurrentAvailableNumberOfSpaces(50);
        parking.setAllocatedVehicles(new ArrayList<>());
    }

    @Test
    public void testSave(){
        inMemoryParkingRepository.save(parking);
        Parking retrivedParking = inMemoryParkingRepository.findById(parking.getId());
        assertEquals(retrivedParking.getId(),parking.getId());
    }

    @Test
    public void testFindById(){
        //CASE INCORRECT ID
        Parking parking1 = inMemoryParkingRepository.findById(UUID.randomUUID());
        assertNull(parking1);//Not found id = null object

        //CASE CORRECT ID
        inMemoryParkingRepository.save(parking);
        Parking retrivedParking = inMemoryParkingRepository.findById(parking.getId());
        assertEquals(retrivedParking.getId(),parking.getId());
    }

    @Test
    public void testFindAll(){
        List<Parking> parkings = inMemoryParkingRepository.findAll();
        assertEquals(0,parkings.size());
    }

    @Test
    public void testDeleteById(){
        inMemoryParkingRepository.save(parking);
        inMemoryParkingRepository.deleteById(parking.getId());
        List<Parking> parkings = inMemoryParkingRepository.findAll();
        assertEquals(0,parkings.size());
    }
}
