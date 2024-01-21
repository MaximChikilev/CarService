package com.example.carservice.services;

import com.example.carservice.entity.MaintenanceSchedule;
import com.example.carservice.repo.CarRepository;
import com.example.carservice.repo.MaintenanceScheduleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
@Service
public class MaintenanceScheduleService extends EntityService<MaintenanceSchedule>{
    protected MaintenanceScheduleService(MaintenanceScheduleRepository repository) {
        super(repository);
    }

    @Override
    protected Map<String, Function<String, List<MaintenanceSchedule>>> setSearchFieldsAndCorrespondingMethods() {
        methodMap.put("Technical inspection name",((MaintenanceScheduleRepository) repository)::findAllByTechnicalInspectionNameContaining);
        methodMap.put("Car license plate number",((MaintenanceScheduleRepository) repository)::findAllByCarLicensePlateNumberContaining);
        methodMap.put("Client phone number",((MaintenanceScheduleRepository) repository)::findAllByClientPhoneNumberContaining);
        return methodMap;
    }
}
