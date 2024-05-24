package es.uca.dss.parkcontrol.web_ui.views.manager_pages.subscription_management_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.SubscriptionType;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Route(value = "/manager/change-subscription-type-price")
@PageTitle("Change Subscription Type Price")
public class ChangeSubscriptionTypePriceView extends VerticalLayout {

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);

    public ChangeSubscriptionTypePriceView() {
        addClassName("change-subscription-type-price-view");
        add(new H1("Change Subscription Type Price"));
        requestFactory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(requestFactory);

        FormLayout formLayout = new FormLayout();

        Select<SubscriptionType> subscriptionTypeSelect = new Select<>();
        subscriptionTypeSelect.setLabel("Subscription Type");
        subscriptionTypeSelect.setItemLabelGenerator(SubscriptionType::getName);
        subscriptionTypeSelect.setItems(Arrays.asList(restTemplate.getForObject("http://localhost:8080/api/v1/managers/subscriptionTypes", SubscriptionType[].class)));

        NumberField priceField = new NumberField("New Price");

        formLayout.add(subscriptionTypeSelect, priceField);

        Button updateButton = new Button("Update");
        updateButton.addClickListener(e -> {
            SubscriptionType selectedSubscriptionType = subscriptionTypeSelect.getValue();
            selectedSubscriptionType.setPrice(priceField.getValue());

            restTemplate.patchForObject("http://localhost:8080/api/v1/managers/subscription/" + selectedSubscriptionType.getName() + "?newPrice=" + priceField.getValue().toString(), selectedSubscriptionType, Void.class);

            Notification successNotification = new Notification("Subscription price was successfully changed", 3000, Notification.Position.TOP_CENTER);
            successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            successNotification.open();
        });

        formLayout.add(updateButton);

        add(formLayout);

        Button goToManagerOptions = new Button("Back to options");
        goToManagerOptions.addClickListener(e -> {
            UI.getCurrent().navigate("/manager-options");
        });
        add(goToManagerOptions);
    }
}