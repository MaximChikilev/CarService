package com.example.carservice.services;

import com.example.carservice.dto.ClientCarRecommendedToServiceDTO;
import com.example.carservice.entity.MaintenanceSchedule;
import com.example.carservice.jsonLoaders.manager.ScheduleJsonManager;
import com.example.carservice.repo.MaintenanceScheduleRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class MaintenanceScheduleService extends EntityService<MaintenanceSchedule> {
    private final ScheduleJsonManager scheduleJsonManager;
    private final CarService carService;
    private final ClientService clientService;
    private final InspectionService inspectionService;

    protected MaintenanceScheduleService(MaintenanceScheduleRepository repository, ScheduleJsonManager scheduleJsonManager,
                                         CarService carService, ClientService clientService, InspectionService inspectionService) {
        super(repository);
        this.scheduleJsonManager = scheduleJsonManager;
        this.carService = carService;
        this.clientService = clientService;
        this.inspectionService = inspectionService;
    }

    @Override
    protected Map<String, Function<String, List<MaintenanceSchedule>>> setSearchFieldsAndCorrespondingMethods() {
        methodMap.put("Technical inspection name", ((MaintenanceScheduleRepository) repository)::findAllByTechnicalInspectionNameContaining);
        methodMap.put("Car license plate number", ((MaintenanceScheduleRepository) repository)::findAllByCarLicensePlateNumberContaining);
        methodMap.put("Client phone number", ((MaintenanceScheduleRepository) repository)::findAllByClientPhoneNumberContaining);
        return methodMap;
    }

    @Override
    protected List<MaintenanceSchedule> loadEntityListFromJson() throws IOException {
        var schedules = scheduleJsonManager.loadListFromFile();
        for (MaintenanceSchedule schedule : schedules) {
            if (schedule.getCar() != null)
                schedule.setCar(carService.getByLicencePlateNumber(schedule.getCar().getLicensePlateNumber()));
            if (schedule.getClient() != null)
                schedule.setClient(clientService.getClientByEmail(schedule.getClient().getEmail()));
            if (schedule.getTechnicalInspection() != null)
                schedule.setTechnicalInspection(inspectionService.getByInspectionName(schedule.getTechnicalInspection().getName()));
        }
        return schedules;
    }
    public List<MaintenanceSchedule> getScheduleRegisteredForServiceDuringThePeriod(Date endDate) {
        var schedules = ((MaintenanceScheduleRepository)repository).findByMaintenanceDateBetween(Utils.getTodayDate(),endDate);
        return schedules;
    }


}
