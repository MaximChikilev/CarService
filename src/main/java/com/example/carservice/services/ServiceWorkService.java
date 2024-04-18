package com.example.carservice.services;

import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.entity.Car;
import com.example.carservice.jsonLoaders.manager.ServiceWorkJsonManager;
import com.example.carservice.repo.ServiceWorkRepository;
import com.example.carservice.entity.ServiceWork;
import com.example.carservice.entity.SparePart;
import com.example.carservice.repo.SparePartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  public Long getId(ServiceWork entity) {
    return entity.getServiceWorkId();
  }

  @Transactional
  @Override
  public boolean isDataErrorPresent(ServiceWork entity) {
    boolean result = false;
    ServiceWork serviceWork = getByName(entity.getName());
    if (serviceWork != null) result = true;
    return result;
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

  @Transactional
  @Override
  public List<ServiceWork> loadEntityListFromJson() throws IOException {
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

  @Transactional
  @Override
  public List<ConnectionsWithOtherEntityDTO> getConnectionsWithOtherTables(Long id) {
    List<ConnectionsWithOtherEntityDTO> list = new ArrayList<>();
    list.addAll(((ServiceWorkRepository) repository).getConnectionWithTechnicalInspection(id));
    return list;
  }

  @Transactional
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

  @Transactional
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

  @Transactional
  public List<ServiceWork> replacingObjectsAfterDeserializationWithReal(
      List<ServiceWork> inputList) {
    var targetList = new ArrayList<ServiceWork>();
    for (ServiceWork serviceWork : inputList) {
      var realServiceWork = ((ServiceWorkRepository) repository).findByName(serviceWork.getName());
      if (realServiceWork != null) targetList.add(realServiceWork);
    }
    return targetList;
  }

  @Transactional
  public ServiceWork getByName(String name) {
    return ((ServiceWorkRepository) repository).findByName(name);
  }
}
