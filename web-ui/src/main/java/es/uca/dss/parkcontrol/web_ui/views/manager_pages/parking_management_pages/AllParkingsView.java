package es.uca.dss.parkcontrol.web_ui.views.manager_pages.parking_management_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Parking;
import org.springframework.web.client.RestTemplate;

@Route(value = "/manager/all-parkings")
@PageTitle("All Parkings")
public class AllParkingsView extends VerticalLayout {

    private Grid<Parking> grid = new Grid<>(Parking.class);

    RestTemplate restTemplate = new RestTemplate();
    Parking[] parkings = restTemplate.getForObject("http://localhost:8080/api/v1/managers/parking", Parking[].class);

    public AllParkingsView() {
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(new H1("List of all parkings"));
        // Button to go back to options page
        Button goBackToOptions = new Button("Go back to options");
        goBackToOptions.addClickListener(e -> {
            UI.getCurrent().navigate("manager-options");
        });
        add(goBackToOptions);

        if (parkings.length == 0) {
            add(new Paragraph("Sorry, there are no parkings yet."));
        } else {
            add(grid);
            updateList();
        }
    }

    private void configureGrid() {
        grid.addClassName("parking-grid");
        grid.setSizeFull();
        grid.removeAllColumns();

        grid.addColumn(Parking::getName).setHeader("Parking Name");
        grid.addColumn(Parking::getMaxNumberOfSpaces).setHeader("Max Spaces");
        grid.addColumn(Parking::getZipCode).setHeader("ZIP Code");
        grid.addColumn(Parking::getCurrentAvailableNumberOfSpaces).setHeader("Available Spaces");
        grid.addColumn(Parking::getId).setHeader("ID").setAutoWidth(true);
    }

    private void updateList() {
        grid.setItems(parkings);
    }
}