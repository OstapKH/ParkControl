package es.uca.dss.parkcontrol.web_ui.views.manager_pages.parking_management_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Parking;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Vehicle;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Route(value = "/manager/vehicles-in-selected-parking")
@PageTitle("Vehicles in Selected Parking")
public class AllVehiclesInParkingView extends VerticalLayout {

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

    RestTemplate restTemplate = new RestTemplate(requestFactory);

    private Grid<Vehicle> vehicleGrid = new Grid<>(Vehicle.class);

    public AllVehiclesInParkingView() {
        addClassName("vehicles-in-selected-parking-view");

        requestFactory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(requestFactory);

        add(new H1("Vehicles in Selected Parking"));

        ComboBox<Parking> parkingComboBox = new ComboBox<>("Select a Parking");
        List<Parking> parkings = Arrays.asList(restTemplate.getForObject("http://localhost:8080/api/v1/managers/parking", Parking[].class));
        parkingComboBox.setItems(parkings);
        parkingComboBox.setItemLabelGenerator(Parking::getName);
        add(parkingComboBox);

        parkingComboBox.addValueChangeListener(event -> {
            Parking selectedParking = event.getValue();
            if (selectedParking != null) {
                List<Vehicle> vehicles = Arrays.asList(restTemplate.getForObject("http://localhost:8080/api/v1/managers/parking/" + selectedParking.getId() + "/vehicles", Vehicle[].class));
                if (vehicles.isEmpty()) {
                    com.vaadin.flow.component.notification.Notification successNotification = new com.vaadin.flow.component.notification.Notification("There are no vehicles in this parking.", 3000, Notification.Position.TOP_CENTER);
                    successNotification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                    successNotification.open();
                } else {
                    com.vaadin.flow.component.notification.Notification successNotification = new com.vaadin.flow.component.notification.Notification("Vehicles in this parking are shown below.", 3000, Notification.Position.TOP_CENTER);
                    successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    successNotification.open();
                }
                vehicleGrid.setItems(vehicles);
            } else {
                com.vaadin.flow.component.notification.Notification successNotification = new com.vaadin.flow.component.notification.Notification("Error occurred, try again.", 3000, Notification.Position.TOP_CENTER);
                successNotification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                successNotification.open();
                vehicleGrid.setItems();
            }
        });

        vehicleGrid.setColumns("id", "registrationNumber");
        add(vehicleGrid);

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setPadding(true);
        setSpacing(true);

        Button backToOptionsButton = new Button("Back to Options");
        backToOptionsButton.addClickListener(e -> {
            UI.getCurrent().navigate("manager-options");
        });
        add(backToOptionsButton);
    }
}