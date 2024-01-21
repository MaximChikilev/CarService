package com.example.carservice.services;

import com.example.carservice.repo.ClientRepository;
import com.example.carservice.entity.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class ClientService extends EntityService<Client> {
    protected ClientService(ClientRepository repository) {
        super(repository);
    }

    @Override
    protected Map<String, Function<String, List<Client>>> setSearchFieldsAndCorrespondingMethods() {
        methodMap.put("First name",((ClientRepository) repository)::findAllByFirstNameContaining);
        methodMap.put("Second name",((ClientRepository) repository)::findAllBySecondNameContaining);
        methodMap.put("Phone number",((ClientRepository) repository)::findAllByPhoneNumberContaining);
        methodMap.put("Address",((ClientRepository) repository)::findAllByAddressContaining);
        return methodMap;
    }
}
