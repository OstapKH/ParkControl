package es.uca.dss.ParkControl.core.Parking;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
public class ParkingTest {

    private Parking parking;

    @Before
    public void setUp(){
        parking = new Parking();
        parking.setId(UUID.randomUUID());
        parking.setName("Example Parking");
        parking.setZipCode("12345");
        parking.setMaxNumberOfSpaces(100);
        parking.setCurrentAvailableNumberOfSpaces(50);
        parking.setAllocatedVehicles(new ArrayList<>());
    }

    @Test //Los setters se prueban durante Before, y se comprueban aqui
    public void testGettersAndSetters(){
        assertEquals(parking.getId(), parking.getId());
        assertEquals("Example Parking",parking.getName());
        assertEquals("12345",parking.getZipCode());
        assertEquals(100, parking.getMaxNumberOfSpaces());
        assertEquals(50,parking.getCurrentAvailableNumberOfSpaces());
        assertNotNull(parking.getAllocatedVehicles());
    }

    @Test
    public void testEquals(){
        Parking tryparking = new Parking();
        tryparking.setId(parking.getId());
        assertEquals(tryparking,parking);
    }

    @Test
    public void testHashCode(){

    }
}
