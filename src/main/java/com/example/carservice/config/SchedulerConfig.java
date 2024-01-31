package com.example.carservice.config;

import com.example.carservice.dto.ClientCarRecommendedToServiceDTO;
import com.example.carservice.entity.MaintenanceSchedule;
import com.example.carservice.services.ClientService;
import com.example.carservice.services.GpsTrackerManagerService;
import com.example.carservice.services.MaintenanceScheduleService;
import com.example.carservice.services.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    private final GpsTrackerManagerService gpsTrackerManagerService;
    private final MaintenanceScheduleService maintenanceScheduleService;
    private final ClientService clientService;
    private final Utils utils;
    @Value("${daysToService}")
    private int daysToService;

    public SchedulerConfig(GpsTrackerManagerService gpsTrackerManagerService, MaintenanceScheduleService maintenanceScheduleService, ClientService clientService, Utils utils) {
        this.gpsTrackerManagerService = gpsTrackerManagerService;
        this.maintenanceScheduleService = maintenanceScheduleService;
        this.clientService = clientService;
        this.utils = utils;
    }

    @Scheduled(cron = "${grsTrackerData.schedule}")
    public void sendGpsTrackerData() {
        gpsTrackerManagerService.uploadGpsTrackerData();
    }

    @Scheduled(cron = "${remindingCustomersComeForService.schedule}")
    public void sendLettersToClientsScheduledForService() {
        List<MaintenanceSchedule> schedules = maintenanceScheduleService.getScheduleRegisteredForServiceDuringThePeriod(utils.getDateNowPlusSomeDays(daysToService));
        for (MaintenanceSchedule schedule : schedules) {

        }
    }
    @Scheduled(cron = "${getOfferToSignUpForService.schedule}")
    public void sendLettersWithOfferToSignUpForService() {
        List<ClientCarRecommendedToServiceDTO> clientCarRecommendedToServiceList = clientService.getClientsCarsRecommendedToService();
        for (ClientCarRecommendedToServiceDTO clientCarRecommendedToService : clientCarRecommendedToServiceList) {

        }
    }
}
