package es.uca.dss.parkcontrol.web_ui.views.user_pages.enterparking_page;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Parking;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.ParkingEnterRequest;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Ticket;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

@Route(value = "/user/enter-parking")
@PageTitle("Enter Parking")
public class EnterParkingView extends VerticalLayout {

    private final RestTemplate restTemplate = new RestTemplate();

    public EnterParkingView() {
        addClassName("enter-parking-view");
        add(new H2("Enter Parking"));


        // Fetch the available parkings from the API
        Parking[] availableParkings = restTemplate.getForObject("http://localhost:8080/api/v1/managers/parking", Parking[].class);

        ComboBox<Parking> parkingComboBox = new ComboBox<>("Select a Parking");
        parkingComboBox.setItems(availableParkings);
        parkingComboBox.setItemLabelGenerator(Parking::getName);
        parkingComboBox.setRenderer(createRenderer());
        parkingComboBox.getStyle().set("--vaadin-combo-box-overlay-width", "16em");
        add(parkingComboBox);

        if (availableParkings != null) {
            parkingComboBox.setItems(availableParkings);
        }

        add(parkingComboBox);

        TextField registrationNumberField = new TextField("Registration Number");
        add(registrationNumberField);


        Button enterWithRegButton = new Button("Enter with Registration Number");
        enterWithRegButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        enterWithRegButton.addClickListener(e -> {
            Parking selectedParking = parkingComboBox.getValue();
            if (selectedParking == null) {
                Notification notification = new Notification("Please select a parking", 3000, Notification.Position.MIDDLE);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
                return;
            }

            UUID parkingId = selectedParking.getId();
            String registrationNumber = registrationNumberField.getValue();
            if (registrationNumber.isEmpty()) {
                Notification notification = new Notification("Please enter a registration number", 3000, Notification.Position.MIDDLE);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
                return;
            }

            try {
                callApiAndShowTicket("http://localhost:8080/api/v1/users/parking/vehicles/enters", registrationNumber, parkingId);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
        add(enterWithRegButton);

        setSpacing(true);

        Button enterWithoutRegButton = new Button("Enter without Registration Number");
        enterWithoutRegButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        enterWithoutRegButton.addClickListener(e -> {
            Parking selectedParking = parkingComboBox.getValue();
            if (selectedParking == null) {
                Notification notification = new Notification("Please select a parking", 3000, Notification.Position.MIDDLE);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
                return;
            }

            UUID parkingId = selectedParking.getId();
            try {
                callApiAndShowTicket("http://localhost:8080/api/v1/users/parking/vehicles/enters", null, parkingId);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        add(enterWithoutRegButton);

        Button goToOptions = new Button("Back to Options");
        goToOptions.addClickListener(e -> {
            UI.getCurrent().navigate("/user-options");
        });
        add(goToOptions);
    }

    private void callApiAndShowTicket(String url, String registrationNumber, UUID parkingId) throws IOException {

        HttpEntity<Ticket> response = restTemplate.postForEntity(url, new ParkingEnterRequest(registrationNumber, parkingId), Ticket.class);

        showTicketDialog(response.getBody().getId());

//        if (response.getStatusCodeValue() == 200 && response.getBody() != null) {
//        } else {
//            Notification notification = new Notification("Failed to get ticket", 3000, Notification.Position.MIDDLE);
//            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
//            notification.open();
//        }
    }

    private void showTicketDialog(UUID ticketId) throws IOException {
        Dialog dialog = new Dialog();
        dialog.add(new H2("You successfully entered parking."));
        dialog.add(new H5("Please keep this ticket ID for future reference"));
        dialog.add(new H5("Ticket ID: " + ticketId.toString()));

        // button to copy ticket id to clipboard
        Button copyButton = new Button("Copy Ticket ID");
        copyButton.addClickListener(e -> {
            String ticketIdText = ticketId.toString();
            UI.getCurrent().getPage().executeJs(
                    "navigator.clipboard.writeText($0).then(function() { " +
                            "  console.log('Text copied to clipboard successfully!'); " +
                            "}).catch(function(error) { " +
                            "  console.error('Could not copy text: ', error); " +
                            "});", ticketIdText);
        });
        dialog.add(copyButton);

        Button closeButton = new Button("Close");
        closeButton.addClickListener(e -> dialog.close());
        dialog.add(closeButton);

        dialog.open();

    }

    private Renderer<Parking> createRenderer() {
        StringBuilder tpl = new StringBuilder();
        tpl.append("<div style=\"display: flex;\">");
        tpl.append("  <div>");
        tpl.append("    ${item.name}");
        tpl.append("    <div style=\"font-size: var(--lumo-font-size-s); color: ${item.textColor};\">Available Spaces: ${item.currentAvailableNumberOfSpaces}</div>");
        tpl.append("  </div>");
        tpl.append("</div>");

        return LitRenderer.<Parking>of(tpl.toString())
                .withProperty("name", Parking::getName)
                .withProperty("currentAvailableNumberOfSpaces", Parking::getCurrentAvailableNumberOfSpaces)
                .withProperty("textColor", parking -> parking.getCurrentAvailableNumberOfSpaces() > 0 ? "green" : "red");
    }

}