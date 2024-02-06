package com.example.carservice.services;

import com.example.carservice.dto.InspectionDTO;
import com.example.carservice.jsonLoaders.manager.TechnicalInspectionJsonManager;
import com.example.carservice.repo.TechnicalInspectionsRepository;
import com.example.carservice.entity.ServiceWork;
import com.example.carservice.entity.TechnicalInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.io.IOException;
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
  protected Map<String, Function<String, List<TechnicalInspection>>>
      setSearchFieldsAndCorrespondingMethods() {
    methodMap.put("Name", ((TechnicalInspectionsRepository) repository)::findAllByNameContaining);
    return methodMap;
  }

  @Override
  protected List<TechnicalInspection> loadEntityListFromJson() throws IOException {
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

  public TechnicalInspection getByInspectionName(String inspectionName) {
    return ((TechnicalInspectionsRepository) repository).findByName(inspectionName);
  }

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

  public List<InspectionDTO> getInspectionNameWithMileageToPass() {
    return ((TechnicalInspectionsRepository) repository).getInspectionNameWithMileageToPass();
  }

  public int getMaxMileageToPass() {
    return ((TechnicalInspectionsRepository) repository).getMaxMileageToPass();
  }
}
