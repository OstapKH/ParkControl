package es.uca.dss.parkcontrol.web_ui.views.manager_pages.subscription_management_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Subscription;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Route(value = "/manager/all-subscriptions")
@PageTitle("All Subscriptions")
public class AllSubscriptionsView extends VerticalLayout {

    private Grid<Subscription> subscriptionGrid = new Grid<>(Subscription.class);

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);

    public AllSubscriptionsView() {
        addClassName("all-subscriptions-view");

        requestFactory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(requestFactory);

        add(new H1("All Subscriptions"));
        add(subscriptionGrid);

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
        List<Subscription> subscriptions = Arrays.asList(restTemplate.getForObject("http://localhost:8080/api/v1/managers/subscriptions", Subscription[].class));
        subscriptionGrid.removeAllColumns();
        subscriptionGrid.addColumn(Subscription::getId).setHeader("Subscription ID");
        subscriptionGrid.addColumn(Subscription::getDateOfPurchase).setHeader("Date of purchase");
        subscriptionGrid.addColumn(subscription -> subscription.getVehicle().getId()).setHeader("Vehicle ID").setAutoWidth(true);
        subscriptionGrid.addColumn(subscription -> subscription.getSubscriptionType().getName()).setHeader("Subscription type");
        subscriptionGrid.setItems(subscriptions);
    }
}