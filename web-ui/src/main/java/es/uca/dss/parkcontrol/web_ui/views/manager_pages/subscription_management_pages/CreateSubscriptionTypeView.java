package es.uca.dss.parkcontrol.web_ui.views.manager_pages.subscription_management_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.SubscriptionType;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Route(value = "/manager/create-subscription-type")
@PageTitle("Create Subscription Type")
public class CreateSubscriptionTypeView extends VerticalLayout {

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);

    public CreateSubscriptionTypeView() {
        addClassName("create-subscription-type-view");

        requestFactory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(requestFactory);

        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField("Name");
        NumberField priceField = new NumberField("Price");

        formLayout.add(nameField, priceField);

        Button createButton = new Button("Create");
        createButton.addClickListener(e -> {
            SubscriptionType newSubscriptionType = new SubscriptionType();
            newSubscriptionType.setName(nameField.getValue());
            newSubscriptionType.setPrice(priceField.getValue());

            restTemplate.postForObject("http://localhost:8080/api/v1/managers/subscription?name=" + nameField.getValue() + "&price=" + priceField.getValue().toString(), newSubscriptionType, UUID.class);

            Notification successNotification = new Notification("Subscription Type was successfully created", 3000, Notification.Position.TOP_CENTER);
            successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            successNotification.open();

            nameField.clear();
            priceField.clear();
        });

        add(new H1("Create Subscription Type"));

        formLayout.add(createButton);

        add(formLayout);

        Button goToManagerOptions = new Button("Back to options");
        goToManagerOptions.addClickListener(e -> {
            UI.getCurrent().navigate("/manager-options");
        });
        add(goToManagerOptions);
    }
}