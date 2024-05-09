package es.uca.dss.ParkControl;

import es.uca.dss.ParkControl.api_http.ParkingEntity.SpringParkingAdapterRepository;
import es.uca.dss.ParkControl.api_http.ParkingEntity.SpringParkingJpaRepository;
import es.uca.dss.ParkControl.api_http.PlanEntity.SpringPlanAdapterRepository;
import es.uca.dss.ParkControl.api_http.PlanEntity.SpringPlanJpaRepository;
import es.uca.dss.ParkControl.api_http.RecordEntity.SpringRecordAdapterRepository;
import es.uca.dss.ParkControl.api_http.RecordEntity.SpringRecordJpaRepository;
import es.uca.dss.ParkControl.api_http.SubscriptionEntity.SpringSubscriptionAdapterRepository;
import es.uca.dss.ParkControl.api_http.SubscriptionEntity.SpringSubscriptionJpaRepository;
import es.uca.dss.ParkControl.api_http.SubscriptionTypeEntity.SpringSubscriptionTypeAdapterRepository;
import es.uca.dss.ParkControl.api_http.SubscriptionTypeEntity.SpringSubscriptionTypeJpaRepository;
import es.uca.dss.ParkControl.api_http.TicketEntity.SpringTicketAdapterRepository;
import es.uca.dss.ParkControl.api_http.TicketEntity.SpringTicketJpaRepository;
import es.uca.dss.ParkControl.api_http.TransactionEntity.SpringTransactionAdapterRepository;
import es.uca.dss.ParkControl.api_http.TransactionEntity.SpringTransactionJpaRepository;
import es.uca.dss.ParkControl.api_http.VehicleEntity.SpringVehicleAdapterRepository;
import es.uca.dss.ParkControl.api_http.VehicleEntity.SpringVehicleJpaRepository;
import es.uca.dss.ParkControl.core.Parking.ParkingRepository;
import es.uca.dss.ParkControl.core.ParkingManagement.ParkingManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    private SpringParkingJpaRepository springParkingJpaRepository;

    private SpringTransactionJpaRepository springTransactionJpaRepository;

    private SpringSubscriptionJpaRepository springSubscriptionJpaRepository;

    private SpringVehicleJpaRepository springVehicleJpaRepository;

    private SpringSubscriptionTypeJpaRepository springSubscriptionTypeJpaRepository;

    private SpringRecordJpaRepository springRecordJpaRepository;

    private SpringPlanJpaRepository springPlanJpaRepository;

    private SpringTicketJpaRepository springTicketJpaRepository;



    @Bean
    public SpringParkingAdapterRepository springParkingAdapterRepository() {
        return new SpringParkingAdapterRepository(springParkingJpaRepository);
    }

    @Bean
    public SpringTransactionAdapterRepository springTransactionAdapterRepository() {
        return new SpringTransactionAdapterRepository(springTransactionJpaRepository);
    }

    @Bean
    public SpringSubscriptionAdapterRepository springSubscriptionAdapterRepository() {
        return new SpringSubscriptionAdapterRepository(springSubscriptionJpaRepository);
    }

    @Bean
    public SpringVehicleAdapterRepository springVehicleAdapterRepository() {
        return new SpringVehicleAdapterRepository(springVehicleJpaRepository);
    }

    @Bean
    @Primary
    public SpringSubscriptionTypeAdapterRepository springSubscriptionTypeAdapterRepository() {
        return new SpringSubscriptionTypeAdapterRepository(springSubscriptionTypeJpaRepository);
    }

    @Bean
    public SpringRecordAdapterRepository springRecordAdapterRepository() {
        return new SpringRecordAdapterRepository(springRecordJpaRepository);
    }

    @Bean
    public SpringPlanAdapterRepository springPlanAdapterRepository() {
        return new SpringPlanAdapterRepository(springPlanJpaRepository);
    }

    @Bean
    public SpringTicketAdapterRepository springTicketAdapterRepository() {
        return new SpringTicketAdapterRepository(springTicketJpaRepository);
    }
}