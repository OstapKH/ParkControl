package es.uca.dss.ParkControl.core.Ticket;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
public class TicketServiceTest {
    private TicketService ticketService;
    private TicketRepository ticketRepository;
    private Ticket ticket;

    @Before
    public void SetUp(){
        ticketRepository = new InMemoryTicketRepository();
        ticketService = new TicketService(ticketRepository);
        ticket = new Ticket();
        ticket.setId(UUID.randomUUID());
    }

    @Test
    public void testCreateTicket(){
        ticketService.createTicket(ticket);
        List<Ticket> tickets = ticketRepository.findAll();
        assertEquals(1,tickets.size());

        Ticket retrievedTicket = ticketRepository.findById(ticket.getId());
        assertEquals(retrievedTicket,ticket);
    }

    @Test
    public void testGetTicket(){
        ticketService.createTicket(ticket);
        Ticket retrievedTicket = ticketService.getTicket(ticket.getId());
        assertEquals(retrievedTicket,ticket);
    }

    @Test
    public void testGetAllTickets(){
        List<Ticket> tickets = ticketRepository.findAll();
        assertEquals(0,tickets.size());
    }

    @Test
    public void testDeleteTicket(){
        ticketService.createTicket(ticket);
        ticketService.deleteTicket(ticket.getId());
        List<Ticket> tickets = ticketRepository.findAll();
        assertEquals(0,tickets.size());
    }

    @Test
    public void testGetTicketQRCodeImage() throws Exception {
        BufferedImage bufferedImage = ticketService.getTicketQRCodeImage(ticket.getId());
        assertNotNull(bufferedImage);

        assertTrue(bufferedImage.getWidth() > 0 && bufferedImage.getHeight() > 0);

        assertEquals(250, bufferedImage.getWidth());
        assertEquals(250, bufferedImage.getHeight());
    }

    @Test
    public void testByteArrayOutputStream() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = ticketService.getTicketQRCodeByteArray(ticket.getId());
        assertNotNull(byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        assertNotNull(byteArray);

        assertTrue(byteArray.length > 0);


    }
}
