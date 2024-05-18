package es.uca.dss.parkcontrol.views.optionspage;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("User Section")
@Route(value = "user-options")

public class UserOptionsView extends VerticalLayout {

    public UserOptionsView() {
        setSpacing(false);
        add(new H1("User Options"));
        VerticalLayout verticalLayout = new VerticalLayout();

        // Create a HorizontalLayout for the buttons
        HorizontalLayout buttonFirstRowLayout = new HorizontalLayout();
        buttonFirstRowLayout.setSpacing(true);

        Button enterParking = new Button("Enter parking");
        enterParking.addClickListener(e -> { /* Your logic here */ });

        Button exitParking = new Button("Exit parking");
        exitParking.addClickListener(e -> { /* Your logic here */ });

        buttonFirstRowLayout.add(enterParking, exitParking);

        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.add(new H2("Enter and Exit parking management"));
        verticalLayout.add(buttonFirstRowLayout);
        verticalLayout.add(new H2("Ticket management"));

        HorizontalLayout buttonThirdRowLayout = new HorizontalLayout();
        buttonFirstRowLayout.setSpacing(true);

        Button getTicketPrice = new Button("Get ticket price");
        getTicketPrice.addClickListener(e -> { /* Your logic here */ });

        Button getTicketFullInformation = new Button("Get ticket full information");
        getTicketFullInformation.addClickListener(e -> { /* Your logic here */ });

        Button paymentOfTheTicket = new Button("Payment of the ticket");
        paymentOfTheTicket.addClickListener(e -> { /* Your logic here */ });

        buttonThirdRowLayout.add(getTicketPrice, getTicketFullInformation, paymentOfTheTicket);
        verticalLayout.add(buttonThirdRowLayout);

        verticalLayout.add(new H2("Subscription Management"));

        HorizontalLayout buttonFourthRowLayout = new HorizontalLayout();
        buttonFirstRowLayout.setSpacing(true);

        Button getAllSubscriptionsPlans = new Button("Get all subscriptions plans");
        getAllSubscriptionsPlans.addClickListener(e -> { /* Your logic here */ });

        Button subscribeVehicle = new Button("Subscription and payment");
        subscribeVehicle.addClickListener(e -> { /* Your logic here */ });

        buttonFourthRowLayout.add(getAllSubscriptionsPlans, subscribeVehicle);
        verticalLayout.add(buttonFourthRowLayout);

        // Button to go to start page
        Button goToStartPage = new Button("Go to Start page");

        goToStartPage.addClickListener(e -> {
            UI.getCurrent().navigate("home");
        });
        goToStartPage.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);

        verticalLayout.add(goToStartPage);

        add(verticalLayout);


        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
