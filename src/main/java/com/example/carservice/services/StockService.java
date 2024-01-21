package com.example.carservice.services;

import com.example.carservice.entity.Stock;
import com.example.carservice.repo.CarRepository;
import com.example.carservice.repo.StockRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
@Service
public class StockService extends EntityService<Stock>{
    protected StockService(JpaRepository<Stock, Long> repository) {
        super(repository);
    }

    @Override
    protected Map<String, Function<String, List<Stock>>> setSearchFieldsAndCorrespondingMethods() {
        methodMap.put("Spare part name",((StockRepository) repository)::findAllByNameContaining);
        return methodMap;
    }
}
