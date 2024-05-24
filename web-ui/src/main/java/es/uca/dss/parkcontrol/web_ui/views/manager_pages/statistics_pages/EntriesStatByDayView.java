package es.uca.dss.parkcontrol.web_ui.views.manager_pages.statistics_pages;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Parking;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Record;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Route(value = "/manager/entries-stat-by-day")
@PageTitle("Entries Statistic By Day")
public class EntriesStatByDayView extends VerticalLayout {

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

    RestTemplate restTemplate = new RestTemplate(requestFactory);

    private Grid<Record> recordGrid = new Grid<>(Record.class);

    public EntriesStatByDayView() {
        addClassName("entries-stat-by-day-view");

        requestFactory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(requestFactory);

        add(new H1("Entries Statistic By Day"));

        ComboBox<Parking> parkingComboBox = new ComboBox<>("Select a Parking");
        List<Parking> parkings = Arrays.asList(restTemplate.getForObject("http://localhost:8080/api/v1/managers/parking", Parking[].class));
        parkingComboBox.setItems(parkings);
        parkingComboBox.setItemLabelGenerator(Parking::getName);
        add(parkingComboBox);

        DatePicker datePicker = new DatePicker();
        datePicker.setLabel("Select a Day");
        add(datePicker);

        Button showButton = new Button("Show Entries");
        showButton.addClickListener(e -> {
            Parking selectedParking = parkingComboBox.getValue();
            LocalDate selectedDate = datePicker.getValue();
            if (selectedParking != null && selectedDate != null) {
                List<Record> records = Arrays.asList(restTemplate.getForObject("http://localhost:8080/api/v1/managers/parking/" + selectedParking.getId() + "/statistics/entries?dayDate=" + selectedDate.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), Record[].class));

                recordGrid.removeAllColumns(); // Remove all existing columns

                recordGrid.addColumn(Record::getId).setHeader("Record ID");
                recordGrid.addColumn(Record::getDateOfEntry).setHeader("Entry Time");
                recordGrid.addColumn(Record::getDateOfExit).setHeader("Exit Time");
                recordGrid.addColumn(record -> record.getTicket().getVehicle().getRegistrationNumber()).setHeader("Vehicle Registration Number");
                recordGrid.addColumn(record -> record.getTicket().getPlan().getPlanType().name()).setHeader("Plan Type");
                recordGrid.addColumn(record -> getTicketPrice(record.getTicket().getId())).setHeader("Ticket Current Price");

                recordGrid.setItems(records);
            }
        });
        add(showButton);

        add(recordGrid);

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

    double getTicketPrice(UUID ticketId){
        return restTemplate.getForObject("http://localhost:8080/api/v1/users/ticket/" + ticketId + "/price", Double.class);
    }

}