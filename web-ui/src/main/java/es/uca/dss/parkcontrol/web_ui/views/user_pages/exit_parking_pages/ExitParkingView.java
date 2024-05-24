package es.uca.dss.parkcontrol.web_ui.views.user_pages.exit_parking_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Parking;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Route(value = "/user/exit-parking")
@PageTitle("Exit Parking")
public class ExitParkingView extends VerticalLayout {

    private final RestTemplate restTemplate = new RestTemplate();

    public ExitParkingView() {
        addClassName("exit-parking-view");
        add(new H2("Exit Parking"));

        // Fetch the available parkings from the API
        Parking[] availableParkings = restTemplate.getForObject("http://localhost:8080/api/v1/managers/parking", Parking[].class);

        ComboBox<Parking> parkingComboBox = new ComboBox<>("Select a Parking");
        parkingComboBox.setItems(availableParkings);
        parkingComboBox.setItemLabelGenerator(Parking::getName);
        parkingComboBox.setRenderer(createRenderer());
        parkingComboBox.getStyle().set("--vaadin-combo-box-overlay-width", "16em");
        add(parkingComboBox);

        TextField registrationNumberField = new TextField("Registration Number");
        add(registrationNumberField);

        TextField ticketIdField = new TextField("Ticket ID");
        add(ticketIdField);

        Button exitWithRegButton = new Button("Exit");
        exitWithRegButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        exitWithRegButton.addClickListener(e -> {
            Parking selectedParking = parkingComboBox.getValue();
            if (selectedParking == null) {
                Notification notification = new Notification("Please select a parking", 3000, Notification.Position.MIDDLE);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
                return;
            }

            UUID parkingId = selectedParking.getId();
            String registrationNumber = registrationNumberField.getValue();
            UUID ticketId = UUID.fromString(ticketIdField.getValue());
            if (registrationNumber.isEmpty() && ticketId.toString().isEmpty()) {
                Notification notification = new Notification("Please enter a registration number or a ticket ID", 3000, Notification.Position.MIDDLE);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
                return;
            }
            if(!registrationNumber.isEmpty()){
                Boolean isVehicheExitSuccessful = restTemplate.postForEntity("http://localhost:8080/api/v1/users/parking/" + parkingId + "/vehicles/exits?ticketId="+ticketId+"&registrationNumber="+registrationNumber, null, Boolean.class).getBody();
                if(Boolean.TRUE.equals(isVehicheExitSuccessful)){
                    Notification.show("Vehicle exited successfully");
                }else{
                    Notification.show("Vehicle exit failed");
                }
            }
            else {
                Boolean isVehicleExitSuccessfull = restTemplate.postForEntity("http://localhost:8080/api/v1/users/parking/" + parkingId + "/vehicles/exits?ticketId="+ticketId, null, Boolean.class).getBody();
                if(Boolean.TRUE.equals(isVehicleExitSuccessfull)){
                    Notification.show("Vehicle exited successfully");
                }else{
                    Notification.show("Vehicle exit failed");
                }
            }
        });
        add(exitWithRegButton);

        Button goToOptions = new Button("Back to Options");
        goToOptions.addClickListener(e -> {
            UI.getCurrent().navigate("/user-options");
        });
        add(goToOptions);
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