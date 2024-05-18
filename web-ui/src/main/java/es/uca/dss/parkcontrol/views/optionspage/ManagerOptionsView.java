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

@PageTitle("Manager Section")
@Route(value = "manager-options")

public class ManagerOptionsView extends VerticalLayout {

    public ManagerOptionsView() {
        setSpacing(false);

        add(new H1("Manager Options"));

        VerticalLayout verticalLayout = new VerticalLayout();

        // Create a HorizontalLayout for the buttons
        HorizontalLayout buttonFirstRowLayout = new HorizontalLayout();
        buttonFirstRowLayout.setSpacing(true);

        Button getAllParkings = new Button("Get all parkings");
        getAllParkings.addClickListener(e -> { /* Your logic here */ });

        Button addParking = new Button("Add parking");
        addParking.addClickListener(e -> { /* Your logic here */ });

        Button getParkingById = new Button("Get parking by id");
        getParkingById.addClickListener(e -> { /* Your logic here */ });

        Button changeParkingDetails = new Button("Change parking details");
        changeParkingDetails.addClickListener(e -> { /* Your logic here */ });

        buttonFirstRowLayout.add(getAllParkings, addParking, getParkingById, changeParkingDetails);

        // Create a HorizontalLayout for the buttons
        HorizontalLayout buttonSecondRowLayout = new HorizontalLayout();
        buttonFirstRowLayout.setSpacing(true);

        Button deleteParking = new Button("Delete parking");
        deleteParking.addClickListener(e -> { /* Your logic here */ });

        Button getAllVehiclesInParking = new Button("Get all vehicles in parking");
        getAllVehiclesInParking.addClickListener(e -> { /* Your logic here */ });

        buttonSecondRowLayout.add(deleteParking, getAllVehiclesInParking);

        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.add(new H2("Parking Management"));
        verticalLayout.add(buttonFirstRowLayout, buttonSecondRowLayout);
        verticalLayout.add(new H2("Statistics Management"));

        HorizontalLayout buttonThirdRowLayout = new HorizontalLayout();
        buttonFirstRowLayout.setSpacing(true);

        Button getEntriesStatisticByDay = new Button("Get entries statistic by day");
        getEntriesStatisticByDay.addClickListener(e -> { /* Your logic here */ });

        Button getExitsStatisticByDay = new Button("Get exits statistic by day");
        getExitsStatisticByDay.addClickListener(e -> { /* Your logic here */ });

        Button getEntriesStatisticByMonth = new Button("Get entries statistic by month");
        getEntriesStatisticByMonth.addClickListener(e -> { /* Your logic here */ });

        Button getExitsStatisticByMonth = new Button("Get exits statistic by month");
        getExitsStatisticByMonth.addClickListener(e -> { /* Your logic here */ });

        buttonThirdRowLayout.add(getEntriesStatisticByDay, getExitsStatisticByDay, getEntriesStatisticByMonth, getExitsStatisticByMonth);
        verticalLayout.add(buttonThirdRowLayout);

        verticalLayout.add(new H2("Subscription Management"));

        HorizontalLayout buttonFourthRowLayout = new HorizontalLayout();
        buttonFirstRowLayout.setSpacing(true);

        Button getAllSubscriptions = new Button("Get all subscriptions");
        getAllSubscriptions.addClickListener(e -> { /* Your logic here */ });

        Button getAllSubscriptionTypes = new Button("Get all subscription types");
        getAllSubscriptionTypes.addClickListener(e -> { /* Your logic here */ });

        Button createSubscriptionType = new Button("Create subscription type");
        createSubscriptionType.addClickListener(e -> { /* Your logic here */ });

        Button changeSubscriptionTypePrice = new Button("Change subscription type price");
        changeSubscriptionTypePrice.addClickListener(e -> { /* Your logic here */ });

        Button deleteSubscriptionType = new Button("Delete subscription type");
        deleteSubscriptionType.addClickListener(e -> { /* Your logic here */ });

        buttonFourthRowLayout.add(getAllSubscriptions, getAllSubscriptionTypes, createSubscriptionType, changeSubscriptionTypePrice, deleteSubscriptionType);
        verticalLayout.add(buttonFourthRowLayout);

        verticalLayout.add(new H2("Plan Management"));

        HorizontalLayout buttonFifthRowLayout = new HorizontalLayout();
        buttonFirstRowLayout.setSpacing(true);

        Button getAllPlans = new Button("Get all plans");
        getAllPlans.addClickListener(e -> { /* Your logic here */ });

        Button createPlan = new Button("Create plan");
        createPlan.addClickListener(e -> { /* Your logic here */ });

        Button changePlanPrice = new Button("Change plan price");
        changePlanPrice.addClickListener(e -> { /* Your logic here */ });

        Button changePlanName = new Button("Change plan name");
        changePlanName.addClickListener(e -> { /* Your logic here */ });

        Button deletePlan = new Button("Delete plan");
        deletePlan.addClickListener(e -> { /* Your logic here */ });

        buttonFifthRowLayout.add(getAllPlans, createPlan, changePlanPrice, changePlanName, deletePlan);
        verticalLayout.add(buttonFifthRowLayout);

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
