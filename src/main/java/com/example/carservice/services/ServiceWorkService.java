package com.example.carservice.services;

import com.example.carservice.jsonLoaders.manager.ServiceWorkJsonManager;
import com.example.carservice.repo.ServiceWorkRepository;
import com.example.carservice.entity.ServiceWork;
import com.example.carservice.entity.SparePart;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ServiceWorkService extends EntityService<ServiceWork> {
  private final SparePartService sparePartService;
  private final ServiceWorkJsonManager serviceWorkJsonManager;

  protected ServiceWorkService(
      ServiceWorkRepository repository,
      SparePartService sparePartService,
      ServiceWorkJsonManager serviceWorkJsonManager) {
    super(repository);
    this.sparePartService = sparePartService;
    this.serviceWorkJsonManager = serviceWorkJsonManager;
  }

  @Override
  protected Map<String, Function<String, List<ServiceWork>>>
      setSearchFieldsAndCorrespondingMethods() {
    methodMap.put("Name", ((ServiceWorkRepository) repository)::findAllByNameContaining);
    methodMap.put("Duration", ((ServiceWorkRepository) repository)::findAllByDuration);
    methodMap.put(
        "Spare part name", ((ServiceWorkRepository) repository)::findBySparePartNameContaining);
    return methodMap;
  }

  @Override
  protected List<ServiceWork> loadEntityListFromJson() throws IOException {
    var serviceWorks = serviceWorkJsonManager.loadListFromFile();
    for (ServiceWork serviceWork : serviceWorks) {
      if (serviceWork.getSpareParts() != null) {
        serviceWork.setSpareParts(
            sparePartService.replacingObjectsAfterDeserializationWithReal(
                serviceWork.getSpareParts()));
      }
    }
    return serviceWorks;
  }

  public void deleteSparePartFromServiceWork(long swId, long spId) {
    Optional<ServiceWork> optionalSw = repository.findById(swId);
    if (!optionalSw.isPresent()) return;
    ServiceWork serviceWork = optionalSw.get();
    List<SparePart> sparePartList = serviceWork.getSpareParts();
    Optional<SparePart> optionalSp =
        sparePartList.stream().filter(sparePart -> sparePart.getSparePartId() == spId).findFirst();
    if (optionalSp.isPresent()) sparePartList.remove(optionalSp.get());
    repository.save(serviceWork);
  }

  public void addSparePartsToServiceWork(Long[] sparePartsId, Long serviceWorkId) {
    Optional<ServiceWork> optionalSw = repository.findById(serviceWorkId);
    if (!optionalSw.isPresent()) return;
    ServiceWork serviceWork = optionalSw.get();
    for (Long element : sparePartsId) {
      SparePart sparePart = sparePartService.getById(element);
      if (sparePart != null) serviceWork.addSparePart(sparePart);
    }
    repository.save(serviceWork);
  }

  public List<ServiceWork> replacingObjectsAfterDeserializationWithReal(
      List<ServiceWork> inputList) {
    var targetList = new ArrayList<ServiceWork>();
    for (ServiceWork serviceWork : inputList) {
      var realServiceWork = ((ServiceWorkRepository) repository).findByName(serviceWork.getName());
      if (realServiceWork != null) targetList.add(realServiceWork);
    }
    return targetList;
  }
}
