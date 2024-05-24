package es.uca.dss.parkcontrol.web_ui.views.manager_pages.parking_management_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Parking;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Route(value = "/manager/add-parking")
@PageTitle("Add Parking")
public class AddParkingView extends VerticalLayout {

    private Grid<Parking> grid = new Grid<>(Parking.class);
    RestTemplate restTemplate = new RestTemplate();

    public AddParkingView() {
        addClassName("add-parking-view");
        setSizeFull();

        add(new H1("Add a new parking"));

        Button goBackToOptions = new Button("Go back to options");
        goBackToOptions.addClickListener(e -> {
            UI.getCurrent().navigate("manager-options");
        });
        add(goBackToOptions);

        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField("Parking Name");
        IntegerField maxSpacesField = new IntegerField("Max Spaces");
        TextField zipCodeField = new TextField("ZIP Code");

        formLayout.add(nameField, maxSpacesField, zipCodeField);



        Button submitButton = new Button("Add Parking");
        submitButton.addClickListener(e -> {
            Parking newParking = new Parking();
            newParking.setName(nameField.getValue());
            newParking.setMaxNumberOfSpaces(maxSpacesField.getValue());
            newParking.setZipCode(zipCodeField.getValue());

            restTemplate.postForObject("http://localhost:8080/api/v1/managers/parking", newParking, UUID.class);

            Notification successNotification = new Notification("Parking was successfully added", 3000, Notification.Position.TOP_CENTER);
            successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            successNotification.open();
            updateList();
        });

        formLayout.add(submitButton);

        add(formLayout);

        configureGrid();
        updateList();


    }

    private void configureGrid() {
        grid.addClassName("parking-grid");
        grid.setSizeFull();
        grid.removeAllColumns();

        grid.addColumn(Parking::getName).setHeader("Parking Name");
        grid.addColumn(Parking::getMaxNumberOfSpaces).setHeader("Max Spaces");
        grid.addColumn(Parking::getZipCode).setHeader("ZIP Code");
        grid.addColumn(Parking::getCurrentAvailableNumberOfSpaces).setHeader("Available Spaces");

        add(grid);
    }

    private void updateList() {
        Parking[] parkings = restTemplate.getForObject("http://localhost:8080/api/v1/managers/parking", Parking[].class);
        grid.setItems(parkings);
    }
}