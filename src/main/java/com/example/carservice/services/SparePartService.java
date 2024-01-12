package com.example.carservice.services;

import com.example.carservice.dao.SparePartRepository;
import com.example.carservice.entity.SparePart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;


@Service
public class SparePartService extends EntityService<SparePart>{

    protected SparePartService(SparePartRepository repository) {
        super(repository);
    }
    @Override
    protected Map<String, Function<String, List<SparePart>>> setSearchFieldsAndCorrespondingMethods() {
        methodMap.put("Name",((SparePartRepository) repository)::findAllByNameContaining);
        methodMap.put("Part number",((SparePartRepository) repository)::findAllByPartNumberContaining);
        return methodMap;
    }
}
