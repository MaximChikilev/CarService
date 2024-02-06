package com.example.carservice.services;

import com.example.carservice.entity.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Service
public class Utils {
  private final EntityService<Car> carService;
  private final EntityService<Manufacturer> manufacturerService;
  private final EntityService<Client> clientService;
  private final EntityService<SparePart> sparePartService;
  private final EntityService<ServiceWork> serviceWorkService;
  private final EntityService<TechnicalInspection> technicalInspectionService;
  private final EntityService<MaintenanceSchedule> maintenanceScheduleService;

  public Utils(
      EntityService<Car> carService,
      EntityService<Manufacturer> manufacturerService,
      EntityService<Client> clientService,
      EntityService<SparePart> sparePartService,
      EntityService<ServiceWork> serviceWorkService,
      EntityService<TechnicalInspection> technicalInspectionService,
      MaintenanceScheduleService maintenanceScheduleService) {
    this.carService = carService;
    this.manufacturerService = manufacturerService;
    this.clientService = clientService;
    this.sparePartService = sparePartService;
    this.serviceWorkService = serviceWorkService;
    this.technicalInspectionService = technicalInspectionService;
    this.maintenanceScheduleService = maintenanceScheduleService;
  }

  public void loadAllEntityLists() {
    try {
      manufacturerService.saveAll(manufacturerService.loadEntityListFromJson());
      carService.saveAll(carService.loadEntityListFromJson());
      clientService.saveAll(clientService.loadEntityListFromJson());
      sparePartService.saveAll(sparePartService.loadEntityListFromJson());
      serviceWorkService.saveAll(serviceWorkService.loadEntityListFromJson());
      technicalInspectionService.saveAll(technicalInspectionService.loadEntityListFromJson());
      maintenanceScheduleService.saveAll(maintenanceScheduleService.loadEntityListFromJson());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static User getCurrentUser() {
    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public boolean isUserHasRole(User user, String role) {
    Collection<GrantedAuthority> roles = user.getAuthorities();
    for (GrantedAuthority auth : roles) {
      if (role.equals(auth.getAuthority())) return true;
    }
    return false;
  }

  public static Date parseDate(String dateString) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return dateFormat.parse(dateString);
    } catch (ParseException e) {
      throw new RuntimeException("Error parsing date", e);
    }
  }

  public static Car getCarWithLicencePlateNumber(String licencePlateNumber) {
    var car = new Car();
    car.setLicensePlateNumber(licencePlateNumber);
    return car;
  }

  public static Client getClientWithEmail(String email) {
    var client = new Client();
    client.setEmail(email);
    return client;
  }

  public static TechnicalInspection getTechnicalInspectionWithName(String name) {
    var inspection = new TechnicalInspection();
    inspection.setName(name);
    return inspection;
  }

  public Date getDateNowPlusSomeDays(int amountDays) {
    LocalDate currentDate = LocalDate.now();
    LocalDate dateInTwoDays = currentDate.plusDays(amountDays);
    return Date.from(dateInTwoDays.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  public static Date getTodayDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    return calendar.getTime();
  }
}
