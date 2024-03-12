package com.example.carservice.services;

import com.example.carservice.dto.CarDateTimeChoiceDTO;
import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.dto.FreeDataTimeForInspectionDTO;
import com.example.carservice.dto.InspectionDTO;
import com.example.carservice.entity.*;
import com.example.carservice.jsonLoaders.manager.ScheduleJsonManager;
import com.example.carservice.repo.MaintenanceScheduleRepository;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

@Service
public class MaintenanceScheduleService extends EntityService<MaintenanceSchedule> {
  private final ScheduleJsonManager scheduleJsonManager;
  private final CarService carService;
  private final ClientService clientService;
  private final InspectionService inspectionService;
  private final UserService userService;

  protected MaintenanceScheduleService(
      MaintenanceScheduleRepository repository,
      ScheduleJsonManager scheduleJsonManager,
      CarService carService,
      ClientService clientService,
      InspectionService inspectionService,
      UserService userService) {
    super(repository);
    this.scheduleJsonManager = scheduleJsonManager;
    this.carService = carService;
    this.clientService = clientService;
    this.inspectionService = inspectionService;
    this.userService = userService;
  }

  @Override
  protected Map<String, Function<String, List<MaintenanceSchedule>>>
      setSearchFieldsAndCorrespondingMethods() {
    methodMap.put(
        "Technical inspection name",
        ((MaintenanceScheduleRepository) repository)::findAllByTechnicalInspectionNameContaining);
    methodMap.put(
        "Car license plate number",
        ((MaintenanceScheduleRepository) repository)::findAllByCarLicensePlateNumberContaining);
    methodMap.put(
        "Client phone number",
        ((MaintenanceScheduleRepository) repository)::findAllByClientPhoneNumberContaining);
    return methodMap;
  }

  @Override
  protected List<MaintenanceSchedule> loadEntityListFromJson() throws IOException {
    var schedules = scheduleJsonManager.loadListFromFile();
    for (MaintenanceSchedule schedule : schedules) {
      if (schedule.getCar() != null)
        schedule.setCar(
            carService.getByLicencePlateNumber(schedule.getCar().getLicensePlateNumber()));
      if (schedule.getClient() != null)
        schedule.setClient(clientService.getClientByEmail(schedule.getClient().getEmail()));
      if (schedule.getTechnicalInspection() != null)
        schedule.setTechnicalInspection(
            inspectionService.getByInspectionName(schedule.getTechnicalInspection().getName()));
    }
    return schedules;
  }

  @Override
  public List<ConnectionsWithOtherEntityDTO> getConnectionsWithOtherTables(Long id) {
    return null;
  }

  public List<MaintenanceSchedule> getScheduleRegisteredForServiceDuringThePeriod(Date endDate) {
    var schedules =
        ((MaintenanceScheduleRepository) repository)
            .findByMaintenanceDateBetween(Utils.getTodayDate(), endDate);
    return schedules;
  }

  public FreeDataTimeForInspectionDTO getFreeMaintenanceSchedulesUntilDate(Date endDate) {
    Date currentDate = Utils.getTodayDate();
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    calendar.setTime(currentDate);
    List<String> freeDataTimes = new ArrayList<>();
    List<TimeWindows> timeWindows = List.of(TimeWindows.values());
    do {
      for (TimeWindows timeWindow : timeWindows) {
        if (((MaintenanceScheduleRepository) repository)
                .findByMaintenanceDateAndAndMaintenanceTime(calendar.getTime(), timeWindow)
            == null)
          freeDataTimes.add(
              dateFormat.format(calendar.getTime()) + "(" + timeWindow.getHours() + ")");
      }
      calendar.add(Calendar.DAY_OF_MONTH, 1);
    } while (calendar.getTime().compareTo(endDate) <= 0);
    return new FreeDataTimeForInspectionDTO(freeDataTimes);
  }

  public void signUpNewInspection(CarDateTimeChoiceDTO carDateTimeChoice) throws ParseException {
    Car car = carService.getByLicencePlateNumber(carDateTimeChoice.getSelectedLicensePlateNumber());
    Client client = getClientByCurrentUser();
    TechnicalInspection inspection = getTechnicalInspectionByCar(car);
    Date date = carDateTimeChoice.getDateFromChoice();
    TimeWindows timeWindows = carDateTimeChoice.getTimeWindowFromChoice();
    repository.save(new MaintenanceSchedule(date, timeWindows, inspection, car, client));
  }

  private Client getClientByCurrentUser() {
    String userLogin = Utils.getCurrentUser().getUsername();
    CustomUser customUser = userService.findByLoginOrEmail(userLogin);
    return clientService.getClientByEmail(customUser.getEmail());
  }

  private TechnicalInspection getTechnicalInspectionByCar(Car car) {
    List<InspectionDTO> inspections = inspectionService.getInspectionNameWithMileageToPass();
    String inspectionNameWithCloserMileageToPass = "";
    int mileageUntilPass = -1;
    for (InspectionDTO inspection : inspections) {
      int difference = inspection.getMileageToPass() - car.getMileage();
      if (mileageUntilPass < 0) {
        mileageUntilPass = difference;
        inspectionNameWithCloserMileageToPass = inspection.getInspectionName();
      } else if (difference < mileageUntilPass) {
        mileageUntilPass = difference;
        inspectionNameWithCloserMileageToPass = inspection.getInspectionName();
      }
    }
    return inspectionService.getByInspectionName(inspectionNameWithCloserMileageToPass);
  }

  public List<MaintenanceSchedule> findByLicensePlateNumberAndDate(String licensePlateNumber) {
    return ((MaintenanceScheduleRepository) repository)
        .findByLicensePlateNumberAndDate(licensePlateNumber, Utils.getTodayDate());
  }
}
