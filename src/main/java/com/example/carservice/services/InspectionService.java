package com.example.carservice.services;

import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.dto.InspectionDTO;
import com.example.carservice.entity.Car;
import com.example.carservice.jsonLoaders.manager.TechnicalInspectionJsonManager;
import com.example.carservice.repo.ClientRepository;
import com.example.carservice.repo.TechnicalInspectionsRepository;
import com.example.carservice.entity.ServiceWork;
import com.example.carservice.entity.TechnicalInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class InspectionService extends EntityService<TechnicalInspection> {
  private final ServiceWorkService serviceWorkService;
  private final TechnicalInspectionJsonManager inspectionJsonManager;

  protected InspectionService(
      JpaRepository<TechnicalInspection, Long> repository,
      ServiceWorkService serviceWorkService,
      TechnicalInspectionJsonManager inspectionJsonManager) {
    super(repository);
    this.serviceWorkService = serviceWorkService;
    this.inspectionJsonManager = inspectionJsonManager;
  }

  @Override
  public Long getId(TechnicalInspection entity) {
    return entity.getInspectionsId();
  }

  @Transactional
  @Override
  public boolean isDataErrorPresent(TechnicalInspection entity) {
    boolean result = false;
    TechnicalInspection technicalInspection = getByInspectionName(entity.getName());
    if ((technicalInspection != null)
        && ((getId(entity) == null) || (!entity.getInspectionsId().equals(technicalInspection.getInspectionsId())))) result = true;
    return result;
  }

  @Override
  protected Map<String, Function<String, List<TechnicalInspection>>>
      setSearchFieldsAndCorrespondingMethods() {
    methodMap.put("Name", ((TechnicalInspectionsRepository) repository)::findAllByNameContaining);
    return methodMap;
  }

  @Transactional
  @Override
  public List<TechnicalInspection> loadEntityListFromJson() throws IOException {
    var technicalInspections = inspectionJsonManager.loadListFromFile();
    for (TechnicalInspection inspection : technicalInspections) {
      if (inspection.getServiceWorks() != null) {
        inspection.setServiceWorks(
            serviceWorkService.replacingObjectsAfterDeserializationWithReal(
                inspection.getServiceWorks()));
        inspection.setDurationInMinutes();
      }
    }
    return technicalInspections;
  }

  @Transactional
  @Override
  public List<ConnectionsWithOtherEntityDTO> getConnectionsWithOtherTables(Long id) {
    List<ConnectionsWithOtherEntityDTO> list = new ArrayList<>();
    list.addAll(
        ((TechnicalInspectionsRepository) repository).getConnectionWithMaintenanceSchedule(id));
    return list;
  }

  @Transactional
  public TechnicalInspection getByInspectionName(String inspectionName) {
    return ((TechnicalInspectionsRepository) repository).findByName(inspectionName);
  }

  @Transactional
  public void deleteServiceWorkFromInspection(long swId, long inspectionId) {
    Optional<TechnicalInspection> optionalInspection = repository.findById(inspectionId);
    if (!optionalInspection.isPresent()) return;
    TechnicalInspection technicalInspection = optionalInspection.get();
    List<ServiceWork> serviceWorkList = technicalInspection.getServiceWorks();
    Optional<ServiceWork> optionalSw =
        serviceWorkList.stream()
            .filter(sparePart -> sparePart.getServiceWorkId() == swId)
            .findFirst();
    if (optionalSw.isPresent()) serviceWorkList.remove(optionalSw.get());
    repository.save(technicalInspection);
  }

  @Transactional
  public void addServiceWorkToInspection(Long[] serviceWorkId, Long inspectionId) {
    Optional<TechnicalInspection> optionalInspection = repository.findById(inspectionId);
    if (!optionalInspection.isPresent()) return;
    TechnicalInspection technicalInspection = optionalInspection.get();
    for (Long element : serviceWorkId) {
      ServiceWork serviceWork = serviceWorkService.getById(element);
      if (serviceWork != null) technicalInspection.addServiceWork(serviceWork);
    }
    repository.save(technicalInspection);
  }

  @Transactional
  public List<InspectionDTO> getInspectionNameWithMileageToPass() {
    return ((TechnicalInspectionsRepository) repository).getInspectionNameWithMileageToPass();
  }

  @Transactional
  public int getMaxMileageToPass() {
    return ((TechnicalInspectionsRepository) repository).getMaxMileageToPass();
  }
}
