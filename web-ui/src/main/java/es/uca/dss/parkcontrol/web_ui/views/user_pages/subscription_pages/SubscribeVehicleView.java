package es.uca.dss.parkcontrol.web_ui.views.user_pages.subscription_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Subscription;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.SubscriptionType;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Route(value = "/user/subscribe-vehicle")
@PageTitle("Subscribe Vehicle")
public class SubscribeVehicleView extends VerticalLayout {

    private TextField registrationNumberField = new TextField("Registration Number");
    private ComboBox<String> subscriptionTypeComboBox = new ComboBox<>("Subscription Type");
    private Button subscribeAndPayButton = new Button("Subscribe and Pay");
    private RestTemplate restTemplate = new RestTemplate();

    public SubscribeVehicleView() {
        addClassName("subscribe-vehicle-view");
        setSizeFull();

        add(new H1("Subscribe Vehicle"));

        List<SubscriptionType> subscriptionTypes = Arrays.asList(restTemplate.getForObject("http://localhost:8080/api/v1/users/subscriptions", SubscriptionType[].class));
        if (subscriptionTypes.isEmpty()) {
            Notification.show("No subscription types found");
        }
        else {
            subscriptionTypeComboBox.setItems(subscriptionTypes.stream().map(SubscriptionType::getName).toArray(String[]::new));
        }


        subscribeAndPayButton.addClickListener(e -> {
            String registrationNumber = registrationNumberField.getValue();
            String subscriptionType = subscriptionTypeComboBox.getValue();
            try {
                UUID subscriptionId = restTemplate.postForObject("http://localhost:8080/api/v1/users/subscription/subscribe?registrationNumber=" + registrationNumber + "&subscriptionTypeName=" + subscriptionType, null, Subscription.class).getId();
                restTemplate.postForObject("http://localhost:8080/api/v1/users/subscription/" + subscriptionId + "/payment/card", null, Void.class);
                Notification.show("Subscription and payment successful");
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                    Notification.show("Subscription type not found");
                } else {
                    throw ex;
                }
            }
        });

        Button goBackToOptions = new Button("Back to options");
        goBackToOptions.addClickListener(e -> {
            UI.getCurrent().navigate("/user-options");
        });

        add(registrationNumberField, subscriptionTypeComboBox, subscribeAndPayButton, goBackToOptions);
    }
}