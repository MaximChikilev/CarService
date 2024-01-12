package com.example.carservice.services;

import com.example.carservice.dao.ClientRepository;
import com.example.carservice.dao.ServiceWorkRepository;
import com.example.carservice.entity.ServiceWork;
import com.example.carservice.entity.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
@Service
public class ServiceWorkService extends EntityService<ServiceWork>{
    protected ServiceWorkService(ServiceWorkRepository repository) {
        super(repository);
    }

    @Override
    protected Map<String, Function<String, List<ServiceWork>>> setSearchFieldsAndCorrespondingMethods() {
        methodMap.put("Name",((ServiceWorkRepository) repository)::findAllByNameContaining);
        methodMap.put("Duration",((ServiceWorkRepository) repository):: findAllByDuration);
        methodMap.put("Spare part name",((ServiceWorkRepository) repository)::findBySparePartNameContaining);
        return methodMap;
    }
    public void deleteSparePartFromServiceWork(long swId, long spId){
        Optional<ServiceWork> optionalSw = repository.findById(swId);
        if(!optionalSw.isPresent()) return;
        ServiceWork serviceWork = optionalSw.get();
        List<SparePart> sparePartList = serviceWork.getSpareParts();
        Optional<SparePart> optionalSp = sparePartList.stream()
                .filter(sparePart -> sparePart.getSparePartId() == spId)
                .findFirst();
        if(optionalSp.isPresent()) sparePartList.remove(optionalSp.get());
        repository.save(serviceWork);
    }
}
