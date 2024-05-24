package es.uca.dss.parkcontrol.web_ui.views.manager_pages.subscription_management_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.SubscriptionType;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Route(value = "/manager/delete-subscription-type")
@PageTitle("Delete Subscription Type")
public class DeleteSubscriptionTypeView extends VerticalLayout {

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);

    private Grid<SubscriptionType> subscriptionTypeGrid = new Grid<>(SubscriptionType.class);

    public DeleteSubscriptionTypeView() {
        addClassName("delete-subscription-type-view");

        requestFactory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(requestFactory);

        add(new H1("Delete Subscription Type"));

        subscriptionTypeGrid.setColumns("id", "name", "price");
        subscriptionTypeGrid.setItems(restTemplate.getForObject("http://localhost:8080/api/v1/managers/subscriptionTypes", SubscriptionType[].class));
        subscriptionTypeGrid.addColumn(new ComponentRenderer<>(subscriptionType -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                restTemplate.delete("http://localhost:8080/api/v1/managers/subscription/" + subscriptionType.getName());
                UI.getCurrent().getPage().reload();
            });
            return deleteButton;
        })).setHeader("Actions");

        add(subscriptionTypeGrid);
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