package com.example.carservice.jsonLoaders.deserializer;

import com.example.carservice.entity.*;
import com.example.carservice.services.Utils;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ScheduleDeserializer implements JsonDeserializer<MaintenanceSchedule> {

    @Override
    public MaintenanceSchedule deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        var schedule = new MaintenanceSchedule();
        schedule.setMaintenanceDate(Utils.parseDate(jsonObject.getAsJsonPrimitive("maintenanceDate").getAsString()));
        schedule.setMaintenanceTime(TimeWindows.valueOf(jsonObject.getAsJsonPrimitive("maintenanceTime").getAsString()));
        if (jsonObject.has("technicalInspection")) {
            String technicalInspectionName = jsonObject.getAsJsonPrimitive("technicalInspection").getAsString();
            schedule.setTechnicalInspection(Utils.getTechnicalInspectionWithName(technicalInspectionName));
        }
        if (jsonObject.has("car")) {
            String licencePlateNumber = jsonObject.getAsJsonPrimitive("car").getAsString();
            schedule.setCar(Utils.getCarWithLicencePlateNumber(licencePlateNumber));
        }
        if (jsonObject.has("client")) {
            String email = jsonObject.getAsJsonPrimitive("client").getAsString();
            schedule.setClient(Utils.getClientWithEmail(email));
        }
        return schedule;
    }


}
