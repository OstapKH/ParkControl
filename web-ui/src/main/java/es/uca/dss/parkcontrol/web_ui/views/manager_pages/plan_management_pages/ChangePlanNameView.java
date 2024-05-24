package es.uca.dss.parkcontrol.web_ui.views.manager_pages.plan_management_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import es.uca.dss.parkcontrol.web_ui.views.entities_classes.Plan;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Route(value = "/manager/change-plan-name")
@PageTitle("Change Plan Name")
public class ChangePlanNameView extends VerticalLayout {

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);

    public ChangePlanNameView() {
        addClassName("change-plan-name-view");
        add(new H2("Change Plan Name"));

        requestFactory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(requestFactory);

        FormLayout formLayout = new FormLayout();

        ComboBox<Plan> planComboBox = new ComboBox<>("Plan");
        planComboBox.setItemLabelGenerator(Plan::getPlanName);
        Plan[] plans = restTemplate.getForObject("http://localhost:8080/api/v1/managers/plans", Plan[].class);
        planComboBox.setItems(plans);

        TextField newNameField = new TextField("New Name");

        formLayout.add(planComboBox, newNameField);

        Button changeButton = new Button("Change");
        changeButton.addClickListener(e -> {
            Plan selectedPlan = planComboBox.getValue();
            if (selectedPlan != null) {
                restTemplate.put("http://localhost:8080/api/v1/managers/plan/" + selectedPlan.getPlanName() + "/name?newName=" + newNameField.getValue(), null);
                Notification.show("Plan Name Changed Successfully");
                planComboBox.clear();
                newNameField.clear();
                planComboBox.setItems(restTemplate.getForObject("http://localhost:8080/api/v1/managers/plans", Plan[].class));
            } else {
                Notification.show("Please select a plan");
            }
        });

        formLayout.add(changeButton);

        add(formLayout);

        Button goToManagerOptions = new Button("Back to options");
        goToManagerOptions.addClickListener(e -> {
            UI.getCurrent().navigate("/manager-options");
        });
        add(goToManagerOptions);
    }
}