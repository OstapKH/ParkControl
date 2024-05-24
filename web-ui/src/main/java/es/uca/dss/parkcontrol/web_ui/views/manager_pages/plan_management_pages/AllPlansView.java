package es.uca.dss.parkcontrol.web_ui.views.manager_pages.plan_management_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Plan;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Route(value = "/manager/all-plans")
@PageTitle("All Plans")
public class AllPlansView extends VerticalLayout {

    private Grid<Plan> planGrid = new Grid<>(Plan.class);

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);

    public AllPlansView() {
        addClassName("all-plans-view");

        requestFactory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(requestFactory);

        add(new H1("All Plans"));
        add(planGrid);

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setPadding(true);
        setSpacing(true);

        updateGrid();

        Button goToManagerOptions = new Button("Back to options");
        goToManagerOptions.addClickListener(e -> {
            UI.getCurrent().navigate("/manager-options");
        });
        add(goToManagerOptions);
    }

    private void updateGrid() {
        List<Plan> plans = Arrays.asList(restTemplate.getForObject("http://localhost:8080/api/v1/managers/plans", Plan[].class));
        planGrid.removeAllColumns();
        planGrid.addColumn(Plan::getId).setHeader("Plan ID");
        planGrid.addColumn(Plan::getPlanName).setHeader("Name");
        planGrid.addColumn(Plan::getPrice).setHeader("Price");
        planGrid.setItems(plans);
    }
}