package es.uca.dss.ParkControl.core.Ticket;

import es.uca.dss.ParkControl.core.QRCodeGeneration.QRCodeGenerator;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {
    private TicketRepository repository;

    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public void createTicket(Ticket ticket) {
        repository.save(ticket);
    }

    public Ticket getTicket(UUID id) {
        return repository.findById(id);
    }

    public List<Ticket> getAllTickets() {
        return repository.findAll();
    }

    public Ticket getLatestTicket(UUID vehicleId) {
        List<Ticket> tickets = repository.findByAllByVehicleId(vehicleId);
        return tickets.get(tickets.size() - 1);
    }

    public void deleteTicket(UUID id) {
        repository.deleteById(id);
    }

    //Get Ticket QR code BufferedImage representation
    public BufferedImage getTicketQRCodeImage(UUID id) throws Exception {
        return QRCodeGenerator.generateQRCodeImage(id);
    }
    //Get Ticket QR code ByteArrayOutputStream representation
    public ByteArrayOutputStream getTicketQRCodeByteArray(UUID id) throws Exception {
        return QRCodeGenerator.generateQRCodeByteOutput(id);
    }

}
