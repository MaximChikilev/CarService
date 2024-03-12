package com.example.carservice.services;

import com.example.carservice.dto.ClientCarAVGMileageDTO;
import com.example.carservice.dto.ClientCarRecommendedToServiceDTO;
import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.dto.InspectionDTO;
import com.example.carservice.jsonLoaders.manager.ClientJsonManager;
import com.example.carservice.repo.ClientRepository;
import com.example.carservice.entity.Client;
import com.example.carservice.repo.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class ClientService extends EntityService<Client> {
  private final ClientJsonManager clientJsonManager;
  private final InspectionService inspectionService;
  private final UserService userService;
  private final CarService carService;

  @Value("${daysBeforeService}")
  private int daysBeforeService;

  protected ClientService(
      ClientRepository repository,
      ClientJsonManager clientJsonManager,
      InspectionService inspectionService,
      UserService userService,
      CarService carService) {
    super(repository);
    this.clientJsonManager = clientJsonManager;
    this.inspectionService = inspectionService;
    this.userService = userService;
    this.carService = carService;
  }

  @Override
  protected Map<String, Function<String, List<Client>>> setSearchFieldsAndCorrespondingMethods() {
    methodMap.put("First name", ((ClientRepository) repository)::findAllByFirstNameContaining);
    methodMap.put("Second name", ((ClientRepository) repository)::findAllBySecondNameContaining);
    methodMap.put("Phone number", ((ClientRepository) repository)::findAllByPhoneNumberContaining);
    methodMap.put("Address", ((ClientRepository) repository)::findAllByEmailContaining);
    return methodMap;
  }

  @Override
  protected List<Client> loadEntityListFromJson() throws IOException {
    var clients = clientJsonManager.loadListFromFile();
    for (Client client : clients) {
      if (client.getCar() != null) {
        client.setCar(carService.getByLicencePlateNumber(client.getCar().getLicensePlateNumber()));
      }
      userService.createNewCustomUserFromClientData(client);
    }
    return clients;
  }

  @Override
  public List<ConnectionsWithOtherEntityDTO> getConnectionsWithOtherTables(Long id) {
    List<ConnectionsWithOtherEntityDTO> list = new ArrayList<>();
    list.addAll(((ClientRepository)repository).getConnectionWithMaintenanceSchedule(id));
    return list;
  }

  public Client getClientByEmail(String email) {
    return ((ClientRepository) repository).findByEmail(email);
  }

  public List<ClientCarRecommendedToServiceDTO> getClientsCarsRecommendedToService() {
    var list = new ArrayList<ClientCarRecommendedToServiceDTO>();
    var clientCarAVGMileageList = ((ClientRepository) repository).getClientCarAverageMileage();
    var inspections = inspectionService.getInspectionNameWithMileageToPass();
    var maxMileageToPass = inspectionService.getMaxMileageToPass();

    for (ClientCarAVGMileageDTO clientCarAVGMileage : clientCarAVGMileageList) {
      var numberOfCompleteServiceCycles = clientCarAVGMileage.getMileage() / maxMileageToPass;
      for (InspectionDTO inspection : inspections) {
        int mileageToThisInspection =
            inspection.getMileageToPass()
                - (clientCarAVGMileage.getMileage()
                    - numberOfCompleteServiceCycles * maxMileageToPass);
        if ((mileageToThisInspection > 0)
            && (mileageToThisInspection / clientCarAVGMileage.getAvgMileage()
                < daysBeforeService)) {
          var clientCarRecommendedToService =
              new ClientCarRecommendedToServiceDTO(
                  clientCarAVGMileage, inspection.getInspectionName());
          if (!carService.isCarRegisteredToInspection(clientCarRecommendedToService))
            list.add(clientCarRecommendedToService);
        }
      }
    }
    return list;
  }

  public List<String> getLicensePlateNumbersByEmail(String email) {
    return ((ClientRepository) repository).getLicensePlateNumbersByEmail(email);
  }
}
