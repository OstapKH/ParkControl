package es.uca.dss.parkcontrol.web_ui.views.user_pages.ticket_payment_page;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Route(value = "/user/payment-by-card")
@PageTitle("Payment By Card")
public class PaymentByCardView extends VerticalLayout {

    private TextField idField = new TextField("Ticket ID");
    private Button getPriceButton = new Button("Get Price");
    private Button payNowButton = new Button("Pay Now");
    private RestTemplate restTemplate = new RestTemplate();

    public PaymentByCardView() {
        addClassName("payment-by-card-view");
        setSizeFull();

        add(new H1("Payment By Card"));

        getPriceButton.addClickListener(e -> {
            String id = idField.getValue();
            try {
                UUID.fromString(id); // This will throw an exception if the ID is not a valid UUID
                double price = restTemplate.getForObject("http://localhost:8080/api/v1/users/ticket/" + id + "/price", Double.class);
                Notification.show("The price of the ticket is: " + price + " euros");
            } catch (IllegalArgumentException ex) {
                Notification.show("Invalid ID format");
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                    Notification.show("No ticket found with the provided ID");
                } else {
                    throw ex;
                }
            }
        });

        payNowButton.addClickListener(e -> {
            String id = idField.getValue();
            try {
                restTemplate.postForObject("http://localhost:8080/api/v1/users/parking/payment/card/" + id, null, Void.class);
                Notification.show("Payment successful");
            } catch (IllegalArgumentException ex) {
                Notification.show("Invalid ID format");
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                    Notification.show("No ticket found with the provided ID");
                } else {
                    throw ex;
                }
            }
        });

        Button goBackToOptions = new Button("Back to options");
        goBackToOptions.addClickListener(e -> {
            UI.getCurrent().navigate("/user-options");
        });

        add(idField, getPriceButton, payNowButton, goBackToOptions);
    }
}