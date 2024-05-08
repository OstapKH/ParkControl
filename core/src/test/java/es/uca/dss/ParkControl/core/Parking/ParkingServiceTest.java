package es.uca.dss.ParkControl.core.Parking;

import es.uca.dss.ParkControl.core.Vehicle.Vehicle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
public class ParkingServiceTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private ParkingRepository parkingRepository; //Creamos un objeto de la clase interfaz para seguir el principio DIP, aunque en el fondo es la clase InMemory
    private ParkingService parkingService; //Clase a probar en los tests
    private Parking parking;
    @Before
    public void setUp(){
        parkingRepository = new InMemoryParkingRepository();
        parkingService = new ParkingService(parkingRepository);

        parking = new Parking();
        parking.setId(UUID.randomUUID());
        parking.setName("Example");
        parking.setZipCode("12345");
        parking.setMaxNumberOfSpaces(100);
        parking.setCurrentAvailableNumberOfSpaces(50);
        parking.setAllocatedVehicles(new ArrayList<>());
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }
    @Test
    public void testSaveParking(){
        parkingService.saveParking(parking);
        List<Parking> parkings = parkingService.getAllParkings();
        assertEquals(1,parkings.size()); //Comprobar que hay un nuevo parking

        Parking retrievedParking = parkingRepository.findById(parking.getId());
        //Utilizamos repository para acceder al método interfaz, ya que no debemos usar el método de la clase que probamos
        assertEquals(retrievedParking,parking);
    }

    @Test
    public void testGetParkingById(){
        parkingService.saveParking(parking);
        Parking retrievedParking = parkingService.getParkingById(parking.getId());
        assertEquals(retrievedParking,parking);
        //Test identico en funcionamiento al anterior, pero que permite comprobar por separada el funcionamiento de save y get
    }

    @Test
    public void testGetAllParkins(){
        List<Parking> parkings = parkingService.getAllParkings();
        assertNotNull(parkings); //Entendemos null como no crear la lista
        assertEquals(0,parkings.size());
    }

    @Test
    public void testDeleteParking(){
        parkingService.saveParking(parking);
        parkingService.deleteParking(parking.getId());
        List<Parking> parkins = parkingService.getAllParkings();
        assertEquals(0,parkins.size());
    }

    @Test
    public void testChangeParkingName(){
        System.setOut(new PrintStream(outputStreamCaptor));
        UUID invalidParkingId = UUID.randomUUID();
        //CASE INCORRECT ID

        parkingService.changeParkingName(invalidParkingId,"Example2");
        assertEquals("Parking not found with id: " + invalidParkingId + System.lineSeparator(), outputStreamCaptor.toString());
        outputStreamCaptor.reset();

        //CASE CORRECT
        parkingService.saveParking(parking);
        parkingService.changeParkingName(parking.getId(),"Example2");
        assertEquals("Example2",parking.getName());
    }

    @Test
    public void testIncrementCurrentAvailableSpaces(){
        System.setOut(new PrintStream(outputStreamCaptor));
        UUID invalidParkingId = UUID.randomUUID();
        // CASE INCORRECT ID
        parkingService.incrementCurrentAvailableSpaces(invalidParkingId);
        assertEquals("Parking not found with id: " + invalidParkingId + System.lineSeparator(), outputStreamCaptor.toString());
        outputStreamCaptor.reset();

        //CASE CORRECT
        int CurrentAvailableSpacesPlusOne = parking.getCurrentAvailableNumberOfSpaces() + 1;
        parkingService.saveParking(parking);
        parkingService.incrementCurrentAvailableSpaces(parking.getId());
        assertEquals(CurrentAvailableSpacesPlusOne,parking.getCurrentAvailableNumberOfSpaces());
    }

    @Test
    public void testDecrementCurrentAvailableSpaces() {
        System.setOut(new PrintStream(outputStreamCaptor));
        UUID invalidParkingId = UUID.randomUUID();
        // CASE INCORRECT ID
        parkingService.decrementCurrentAvailableSpaces(invalidParkingId);
        assertEquals("Parking not found with id: " + invalidParkingId + System.lineSeparator(), outputStreamCaptor.toString());
        outputStreamCaptor.reset(); // Reiniciar el capturador de salida

        // CASE INCORRECT: NUMBER OF SPACES 0
        parking.setCurrentAvailableNumberOfSpaces(0);
        parkingRepository.save(parking);
        parkingService.decrementCurrentAvailableSpaces(parking.getId());
        assertTrue(outputStreamCaptor.toString().contains("Maximal amount of cars in the parking: " + parking.getId()));
        outputStreamCaptor.reset();

        //CASE CORRECT
        parking.setCurrentAvailableNumberOfSpaces(3);
        parkingRepository.save(parking);
        parkingService.decrementCurrentAvailableSpaces(parking.getId());
        assertEquals(2, parkingRepository.findById(parking.getId()).getCurrentAvailableNumberOfSpaces());
    }

    @Test
    public void testAddVehicleToParking(){
        System.setOut(new PrintStream(outputStreamCaptor));//Sin esta linea no funciona ningun Equals
        UUID invalidParkingId = UUID.randomUUID();

        Vehicle vehicle = new Vehicle();
        vehicle.setId(UUID.randomUUID());
        vehicle.setRegistrationNumber("12345");
        parkingService.saveParking(parking);

        //CASE INCORRECT
        parkingService.addVehicleToParking(invalidParkingId,vehicle);
        System.setOut(System.out);
        assertEquals("Parking not found with id: " + invalidParkingId + System.lineSeparator(), outputStreamCaptor.toString());
        outputStreamCaptor.reset();

        //CASE CORRECT
        parkingService.addVehicleToParking(parking.getId(),vehicle);
        assertEquals(1,parking.getAllocatedVehicles().size());
    }
}
