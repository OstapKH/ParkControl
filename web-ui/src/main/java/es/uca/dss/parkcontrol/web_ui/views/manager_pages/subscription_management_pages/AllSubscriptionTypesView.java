package es.uca.dss.parkcontrol.web_ui.views.manager_pages.subscription_management_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.SubscriptionType;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Route(value = "/manager/all-subscription-types")
@PageTitle("All Subscription Types")
public class AllSubscriptionTypesView extends VerticalLayout {

    private Grid<SubscriptionType> subscriptionTypeGrid = new Grid<>(SubscriptionType.class);

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);

    public AllSubscriptionTypesView() {
        addClassName("all-subscription-types-view");

        requestFactory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(requestFactory);

        add(new H1("All Subscription Types"));
        add(subscriptionTypeGrid);

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
        List<SubscriptionType> subscriptionTypes = Arrays.asList(restTemplate.getForObject("http://localhost:8080/api/v1/managers/subscriptionTypes", SubscriptionType[].class));
        subscriptionTypeGrid.removeAllColumns();
        subscriptionTypeGrid.addColumn(SubscriptionType::getId).setHeader("Subscription Type ID");
        subscriptionTypeGrid.addColumn(SubscriptionType::getName).setHeader("Name");
        subscriptionTypeGrid.addColumn(SubscriptionType::getPrice).setHeader("Price");
        subscriptionTypeGrid.setItems(subscriptionTypes);
    }
}