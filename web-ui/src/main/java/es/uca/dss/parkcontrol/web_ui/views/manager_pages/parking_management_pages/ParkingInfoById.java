package es.uca.dss.parkcontrol.web_ui.views.manager_pages.parking_management_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Parking;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Route(value = "/manager/parking-info-by-id")
@PageTitle("Parking Info By ID")
public class ParkingInfoById extends VerticalLayout {

    private TextField idField = new TextField("Parking ID");
    private Button searchButton = new Button("Search");
    private RestTemplate restTemplate = new RestTemplate();
    private FormLayout parkingInfoForm = new FormLayout();

    public ParkingInfoById() {
        addClassName("parking-info-by-id-view");
        setSizeFull();

        // Button to go back to options page
        Button goBackToOptions = new Button("Go back to options");
        goBackToOptions.addClickListener(e -> {
            UI.getCurrent().navigate("manager-options");
        });
        add(goBackToOptions);

        searchButton.addClickListener(e -> {
            // Remove previous error messages
            getChildren().filter(component -> component instanceof H3).forEach(this::remove);

            String id = idField.getValue();
            try {
                UUID.fromString(id); // This will throw an exception if the ID is not a valid UUID
                Parking parking = restTemplate.getForObject("http://localhost:8080/api/v1/managers/parking/" + id, Parking.class);
                displayParkingInfo(parking);
            } catch (IllegalArgumentException ex) {
                parkingInfoForm.removeAll();
                add(new H3("Invalid ID format"));
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                    parkingInfoForm.removeAll();
                    add(new H3("No parking found with the provided ID"));
                } else {
                    throw ex;
                }
            }
        });
        add(new H1("Search Parking by ID"), idField, searchButton, parkingInfoForm);
    }

    private void displayParkingInfo(Parking parking) {
        parkingInfoForm.removeAll();
        TextField nameField = new TextField("Name", parking.getName());
        TextField maxSpacesField = new TextField("Max Spaces", String.valueOf(parking.getMaxNumberOfSpaces()));
        TextField zipCodeField = new TextField("ZIP Code", parking.getZipCode());
        TextField availableSpacesField = new TextField("Available Spaces", String.valueOf(parking.getCurrentAvailableNumberOfSpaces()));
        nameField.setReadOnly(true);
        maxSpacesField.setReadOnly(true);
        zipCodeField.setReadOnly(true);
        availableSpacesField.setReadOnly(true);
        nameField.setValue(parking.getName());
        maxSpacesField.setValue(String.valueOf(parking.getMaxNumberOfSpaces()));
        zipCodeField.setValue(parking.getZipCode());
        availableSpacesField.setValue(String.valueOf(parking.getCurrentAvailableNumberOfSpaces()));
        parkingInfoForm.add(nameField, maxSpacesField, zipCodeField, availableSpacesField);
    }
}