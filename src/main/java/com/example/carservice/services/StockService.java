package com.example.carservice.services;

import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.entity.Car;
import com.example.carservice.entity.SparePart;
import com.example.carservice.entity.Stock;
import com.example.carservice.repo.StockRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class StockService extends EntityService<Stock> {
  protected StockService(JpaRepository<Stock, Long> repository) {
    super(repository);
  }

  @Override
  public Long getId(Stock entity) {
    return entity.getId();
  }

  @Transactional
  @Override
  public boolean isDataErrorPresent(Stock entity) {
    boolean result = false;
    Stock stock = getStockBySparePart(entity.getSparePart());
    if (stock != null) result = true;
    return result;
  }

  @Override
  protected Map<String, Function<String, List<Stock>>> setSearchFieldsAndCorrespondingMethods() {
    methodMap.put("Spare part name", ((StockRepository) repository)::findAllByNameContaining);
    return methodMap;
  }

  @Transactional
  @Override
  public List<Stock> loadEntityListFromJson() {
    return null;
  }

  @Transactional
  public Stock getStockBySparePart(SparePart sparePart) {
    return ((StockRepository) repository).findBySparePart(sparePart);
  }

  @Transactional
  @Override
  public List<ConnectionsWithOtherEntityDTO> getConnectionsWithOtherTables(Long id) {
    return null;
  }
}
