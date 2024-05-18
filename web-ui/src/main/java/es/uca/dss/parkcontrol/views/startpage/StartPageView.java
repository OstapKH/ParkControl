package es.uca.dss.parkcontrol.views.startpage;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.UUID;

@PageTitle("ParkControl Web UI")
@Route(value = "home")
@RouteAlias(value = "home")

public class StartPageView extends VerticalLayout {

    public StartPageView() {
        setSpacing(false);

//        RestTemplate restTemplate = new RestTemplate();
//        SubscriptionType[] subscriptionTypesList = restTemplate.getForObject("http://localhost:8080/api/v1/users/subscriptions", SubscriptionType[].class);


        Image img = new Image("images/ParkControlLogo.png", "park control logo");
        img.setWidth("400px");
        add(img);
        add(new Paragraph("Welcome to ParkControl Web UI 🤗"));
        add(new Paragraph("This UI is used to simulate the ParkControl application. Try it out!"));

        // Create a HorizontalLayout for the buttons
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);

        // Create the Manager button
        Button managerButton = new Button("Manager");
        managerButton.addClickListener(e -> { UI.getCurrent().navigate("manager-options"); });

        // Create the User button
        Button userButton = new Button("User");
        userButton.addClickListener(e -> { UI.getCurrent().navigate("user-options"); });

        // Add buttons to the HorizontalLayout
        buttonLayout.add(managerButton, userButton);

        // Add the HorizontalLayout to the main layout
        add(buttonLayout);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    public static class SubscriptionType {
        private UUID id;
        private String name;
        private double price;

        public SubscriptionType() {
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

    }

}
