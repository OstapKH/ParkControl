package es.uca.dss.parkcontrol.web_ui.views.manager_pages.parking_management_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Parking;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.ParkingUpdateRequest;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Route(value = "/manager/change-parking-details")
@PageTitle("Change Parking Details")
public class ChangeParkingDetailsView extends VerticalLayout {

    private TextField idField = new TextField("Parking ID");
    private TextField nameField = new TextField("New Name");
    private IntegerField maxSpacesField = new IntegerField("New Max Spaces");
    private TextField zipCodeField = new TextField("New ZIP Code");
    private Button submitButton = new Button("Submit Changes");

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

    RestTemplate restTemplate = new RestTemplate(requestFactory);

    private Grid<Parking> parkingGrid = new Grid<>(Parking.class);

    public ChangeParkingDetailsView() {
        addClassName("change-parking-details-view");

        requestFactory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(requestFactory);

        add(new H1("Change Parking Details"));


        parkingGrid.setColumns("id", "name", "maxNumberOfSpaces", "zipCode");
        parkingGrid.setItems(restTemplate.getForObject("http://localhost:8080/api/v1/managers/parking", Parking[].class));
        parkingGrid.addColumn(new ComponentRenderer<>(parking -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                idField.setValue(parking.getId().toString());
                nameField.setValue(parking.getName());
                maxSpacesField.setValue(parking.getMaxNumberOfSpaces());
                zipCodeField.setValue(parking.getZipCode());
            });
            return editButton;
        })).setHeader("Actions");

        submitButton.addClickListener(e -> {
            String id = idField.getValue();
            String name = nameField.getValue();
            Integer maxSpaces = maxSpacesField.getValue();
            String zipCode = zipCodeField.getValue();

            restTemplate.patchForObject(
                    "http://localhost:8080/api/v1/managers/parking/" + id,
                    new ParkingUpdateRequest(name, maxSpaces, zipCode),
                    Void.class
            );

            UI.getCurrent().getPage().reload();
        });

        FormLayout formLayout = new FormLayout();
        formLayout.add(idField, nameField, maxSpacesField, zipCodeField, submitButton);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("600px", 2),
                new FormLayout.ResponsiveStep("900px", 3));

        HorizontalLayout formLayoutWrapper = new HorizontalLayout(formLayout);
        formLayoutWrapper.setPadding(true);
        formLayoutWrapper.setSpacing(true);
        formLayoutWrapper.setAlignItems(Alignment.CENTER);

        add(parkingGrid, formLayoutWrapper);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setPadding(true);
        setSpacing(true);

        Button goToOptionsButton = new Button("Go to options page");
        goToOptionsButton.addClickListener(e -> {
            UI.getCurrent().navigate("manager-options");
        });
        add(goToOptionsButton);
    }
}