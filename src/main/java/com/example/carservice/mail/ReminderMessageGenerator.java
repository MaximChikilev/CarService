package com.example.carservice.mail;

import com.example.carservice.entity.MaintenanceSchedule;
import org.springframework.stereotype.Component;

@Component
public class ReminderMessageGenerator extends AbstractMessageGenerator<MaintenanceSchedule> {
  @Override
  protected String getText(MaintenanceSchedule element) {

    return String.format(
        "Dear %s, you have an appointment for service at: %tF (%s)",
        element.getClient().getFirstName(),
        element.getMaintenanceDate(),
        element.getMaintenanceTime());
  }

  @Override
  protected String getSubject() {
    return "Car Service. Reminder to sign up for the service";
  }

  @Override
  protected String getTo(MaintenanceSchedule element) {
    return element.getClient().getEmail();
  }
}
