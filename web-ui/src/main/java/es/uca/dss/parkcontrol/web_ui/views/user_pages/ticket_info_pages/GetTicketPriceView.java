package es.uca.dss.parkcontrol.web_ui.views.user_pages.ticket_info_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Route("/user/get-ticket-price")
@PageTitle("Get Ticket Price")
public class GetTicketPriceView extends VerticalLayout {

    private TextField ticketIdField;
    private Button getPriceButton;
    private Dialog dialog;

    public GetTicketPriceView() {
        ticketIdField = new TextField("Enter Ticket ID");
        getPriceButton = new Button("Get Price");
        Button goToOptions = new Button("Back to Options");
        dialog = new Dialog();

        getPriceButton.addClickListener(e -> {
            String ticketId = ticketIdField.getValue();
            if (ticketId.isEmpty()) {
                Notification.show("Please enter a ticket ID");
            } else {
                try {
                    UUID uuid = UUID.fromString(ticketId);
                    double price = getTicketPrice(uuid);
                    dialog.add("The price of the ticket is: " + price + " euros");
                    dialog.open();
                } catch (IllegalArgumentException ex) {
                    Notification.show("Invalid Ticket ID");
                }
            }
        });

        add(ticketIdField, getPriceButton);
        goToOptions.addClickListener(e -> {
            UI.getCurrent().navigate("/user-options");
        });
        add(goToOptions);
    }

    private double getTicketPrice(UUID ticketId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/v1/users/ticket/" + ticketId + "/price";
        return restTemplate.getForObject(url, Double.class);
    }
}