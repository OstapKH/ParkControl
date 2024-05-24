package es.uca.dss.parkcontrol.web_ui.views.user_pages.ticket_payment_page;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/user/choose-ticket-payment-option")
@PageTitle("Choose Ticket Payment Option")
public class ChooseTicketPaymentOptionView extends VerticalLayout {

    public ChooseTicketPaymentOptionView() {
        addClassName("choose-ticket-payment-option-view");
        setSizeFull();

        add(new H1("Payment option"));

        Button payByCardButton = new Button("Pay by Card");
        payByCardButton.addClickListener(e -> {
            UI.getCurrent().navigate("/user/payment-by-card");
        });

        Button payInCashButton = new Button("Pay in Cash");
        payInCashButton.addClickListener(e -> {
            UI.getCurrent().navigate("/user/payment-in-cash");
        });

        Button goBackToOptions = new Button("Back to options");
        goBackToOptions.addClickListener(e -> {
            UI.getCurrent().navigate("/user-options");
        });

        add(payByCardButton, payInCashButton, goBackToOptions);
    }
}