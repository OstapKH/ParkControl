package es.uca.dss.parkcontrol.web_ui.views.user_pages.ticket_info_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Route(value = "/user/full-ticket-by-id")
@PageTitle("Full Ticket By ID")
public class FullTicketByIdView extends VerticalLayout {

    private TextField idField = new TextField("Ticket ID");
    private Button searchButton = new Button("Search");
    private RestTemplate restTemplate = new RestTemplate();
    private FormLayout ticketInfoForm = new FormLayout();

    public FullTicketByIdView() {
        addClassName("full-ticket-by-id-view");
        setSizeFull();

        // Button to go back to options page
        Button goBackToOptions = new Button("Go back to options");
        goBackToOptions.addClickListener(e -> {
            UI.getCurrent().navigate("/user-options");
        });
        add(goBackToOptions);

        searchButton.addClickListener(e -> {
            // Remove previous error messages
            getChildren().filter(component -> component instanceof H3).forEach(this::remove);

            String id = idField.getValue();
            try {
                UUID.fromString(id); // This will throw an exception if the ID is not a valid UUID
                Ticket ticket = restTemplate.getForObject("http://localhost:8080/api/v1/users/ticket/" + id, Ticket.class);
                displayTicketInfo(ticket);
            } catch (IllegalArgumentException ex) {
                ticketInfoForm.removeAll();
                add(new H3("Invalid ID format"));
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                    ticketInfoForm.removeAll();
                    add(new H3("No ticket found with the provided ID"));
                } else {
                    throw ex;
                }
            }
        });
        add(new H1("Search Ticket by ID"), idField, searchButton, ticketInfoForm);
    }

    private void displayTicketInfo(Ticket ticket) {
        ticketInfoForm.removeAll();
        TextField idField = new TextField("ID");
        idField.setValue(ticket.getId().toString());
        TextField registrationNumberField = new TextField("Registration Number");
        if (ticket.getVehicle().getRegistrationNumber() != null) {
            registrationNumberField.setValue(ticket.getVehicle().getRegistrationNumber());
        } else {
            registrationNumberField.setValue("");
        }
        TextField entryTimeField = new TextField("Entry Time");
        entryTimeField.setValue(ticket.getDateOfIssue().toString());
        idField.setReadOnly(true);
        registrationNumberField.setReadOnly(true);
        entryTimeField.setReadOnly(true);
        TextField parkingField = new TextField("Parking");
        parkingField.setValue(ticket.getParking().getName());
        parkingField.setReadOnly(true);
        TextField planField = new TextField("Plan");
        planField.setValue(ticket.getPlan().getPlanType().name());
        planField.setReadOnly(true);
        ticketInfoForm.add(idField, registrationNumberField, entryTimeField, parkingField, planField);
    }
}