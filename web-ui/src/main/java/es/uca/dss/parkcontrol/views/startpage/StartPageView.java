package es.uca.dss.parkcontrol.views.startpage;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@PageTitle("StartPage")
@Route(value = "")
@RouteAlias(value = "")

public class StartPageView extends VerticalLayout {

    public StartPageView() {
        setSpacing(false);

        RestTemplate restTemplate = new RestTemplate();
        SubscriptionType[] subscriptionTypesList = restTemplate.getForObject("http://localhost:8080/api/v1/users/subscriptions", SubscriptionType[].class);


        Image img = new Image("images/ParkControlLogo.png", "park control logo");
        img.setWidth("400px");
        add(img);
        H2 header = new H2(subscriptionTypesList[0].getName());
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        add(new Paragraph("Welcome to ParkControl Web UI 🤗"));

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
