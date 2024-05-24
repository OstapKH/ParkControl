package es.uca.dss.parkcontrol.web_ui.views.manager_pages.plan_management_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Plan;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.PlanType;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Route(value = "/manager/create-plan")
@PageTitle("Create Plan")
public class CreatePlanView extends VerticalLayout {

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);

    public CreatePlanView() {
        addClassName("create-plan-view");
        add(new H1("Create Plan"));

        requestFactory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(requestFactory);

        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField("Name");
        NumberField priceField = new NumberField("Price");

        Select<String> planTypeSelect = new Select<>();
        planTypeSelect.setLabel("Plan Type");
        planTypeSelect.setItems("MINUTES", "HOURS", "DAYS", "WEEKS");

        formLayout.add(nameField, priceField, planTypeSelect);

        Button createButton = new Button("Create");
        createButton.addClickListener(e -> {
            Plan newPlan = new Plan();
            newPlan.setPlanName(nameField.getValue());
            newPlan.setPrice(priceField.getValue());
            newPlan.setPlanType(PlanType.valueOf(planTypeSelect.getValue().toString()));

            restTemplate.postForObject("http://localhost:8080/api/v1/managers/plan?name=" + nameField.getValue() +"&price=" + priceField.getValue().toString() + "&planType=" + planTypeSelect.getValue(), newPlan, Void.class);

            Notification.show("Plan Created Successfully");
        });

        formLayout.add(createButton);

        add(formLayout);

        Button goToManagerOptions = new Button("Back to options");
        goToManagerOptions.addClickListener(e -> {
            UI.getCurrent().navigate("/manager-options");
        });
        add(goToManagerOptions);
    }
}