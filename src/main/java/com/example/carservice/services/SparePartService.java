package com.example.carservice.services;

import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.entity.Car;
import com.example.carservice.jsonLoaders.manager.SparePartJsonManager;
import com.example.carservice.repo.ClientRepository;
import com.example.carservice.repo.SparePartRepository;
import com.example.carservice.entity.SparePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class SparePartService extends EntityService<SparePart> {
  private final SparePartJsonManager sparePartJsonManager;
  private final ManufacturerService manufacturerService;

  protected SparePartService(
      SparePartRepository repository,
      SparePartJsonManager sparePartJsonManager,
      ManufacturerService manufacturerService) {
    super(repository);

    this.sparePartJsonManager = sparePartJsonManager;
    this.manufacturerService = manufacturerService;
  }

  @Transactional
  @Override
  public boolean isDataErrorPresent(SparePart entity) {
    boolean result = false;
    String name = entity.getName();
    String partNumber = entity.getPartNumber();
    SparePart sparePart = getByNamePartNumber(name, partNumber);
    if ((sparePart != null)
        && ((getId(entity) == null)
            || (!entity.getSparePartId().equals(sparePart.getSparePartId())))) result = true;
    return result;
  }

  @Override
  public Long getId(SparePart entity) {
    return entity.getSparePartId();
  }

  @Override
  protected Map<String, Function<String, List<SparePart>>>
      setSearchFieldsAndCorrespondingMethods() {
    methodMap.put("Name", ((SparePartRepository) repository)::findAllByNameContaining);
    methodMap.put("Part number", ((SparePartRepository) repository)::findAllByPartNumberContaining);
    return methodMap;
  }

  @Transactional
  @Override
  public List<SparePart> loadEntityListFromJson() throws IOException {
    var spareParts = sparePartJsonManager.loadListFromFile();
    for (SparePart sparePart : spareParts) {
      if (sparePart.getManufacturer() != null) {
        sparePart.setManufacturer(
            manufacturerService.getByName(sparePart.getManufacturer().getName()));
      }
    }
    return spareParts;
  }

  @Transactional
  @Override
  public List<ConnectionsWithOtherEntityDTO> getConnectionsWithOtherTables(Long id) {
    List<ConnectionsWithOtherEntityDTO> list = new ArrayList<>();
    list.addAll(((SparePartRepository) repository).getConnectionWithServiceWork(id));
    return list;
  }

  @Transactional
  public List<SparePart> replacingObjectsAfterDeserializationWithReal(List<SparePart> inputList) {
    var targetList = new ArrayList<SparePart>();
    for (SparePart sparePart : inputList) {
      var realSparePart =
          ((SparePartRepository) repository).findByPartNumber(sparePart.getPartNumber());
      if (realSparePart != null) targetList.add(realSparePart);
    }
    return targetList;
  }

  @Transactional
  public SparePart getByNamePartNumber(String name, String partNumber) {
    return ((SparePartRepository) repository)
        .findByNameAndPartNumberAndManufacturer(name, partNumber);
  }
}
