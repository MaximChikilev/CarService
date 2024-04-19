package com.example.carservice.services;

import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.entity.Car;
import com.example.carservice.jsonLoaders.manager.ManufacturerJsonManager;
import com.example.carservice.repo.CarRepository;
import com.example.carservice.repo.ManufacturerRepository;
import com.example.carservice.entity.Manufacturer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class ManufacturerService extends EntityService<Manufacturer> {
  private final ManufacturerJsonManager manufacturerJsonManager;

  protected ManufacturerService(
      ManufacturerRepository repository, ManufacturerJsonManager manufacturerJsonManager) {
    super(repository);
    this.manufacturerJsonManager = manufacturerJsonManager;
  }

  @Override
  public Long getId(Manufacturer entity) {
    return entity.getManufacturerId();
  }

  @Override
  protected Map<String, Function<String, List<Manufacturer>>>
      setSearchFieldsAndCorrespondingMethods() {
    methodMap.put("Name", ((ManufacturerRepository) repository)::findAllByNameContaining);
    return methodMap;
  }

  @Transactional
  @Override
  public List<Manufacturer> loadEntityListFromJson() throws IOException {
    return manufacturerJsonManager.loadListFromFile();
  }

  @Transactional
  @Override
  public List<ConnectionsWithOtherEntityDTO> getConnectionsWithOtherTables(Long id) {
    List<ConnectionsWithOtherEntityDTO> list = new ArrayList<>();
    list.addAll(((ManufacturerRepository) repository).getConnectionWithCar(id));
    list.addAll(((ManufacturerRepository) repository).getConnectionWithSpareParts(id));
    return list;
  }

  public Manufacturer getByName(String name) {
    return ((ManufacturerRepository) repository).findByName(name);
  }

  @Transactional
  @Override
  public boolean isDataErrorPresent(Manufacturer entity) {
    boolean result = false;
    String name = entity.getName();
    Manufacturer manufacturer = getByName(name);
    if ((manufacturer != null)
        && ((getId(entity) == null)
            || (!entity.getManufacturerId().equals(manufacturer.getManufacturerId()))))
      result = true;
    return result;
  }
}
