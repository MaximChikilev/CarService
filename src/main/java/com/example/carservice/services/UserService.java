package com.example.carservice.services;

import com.example.carservice.entity.Client;
import com.example.carservice.entity.CustomUser;
import com.example.carservice.entity.UserRole;
import com.example.carservice.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service

public class UserService extends EntityService<CustomUser> {
    private final PasswordEncoder passwordEncoder;

    protected UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected Map<String, Function<String, List<CustomUser>>> setSearchFieldsAndCorrespondingMethods() {
        methodMap.put("Role", ((UserRepository) repository)::findAllByUserRoleContaining);
        return methodMap;
    }

    @Override
    protected List<CustomUser> loadEntityListFromJson() {
        return null;
    }


    public CustomUser findByLoginOrEmail(String login) {
        return ((UserRepository) repository).findByLogin(login);
    }

    public boolean isUserExist(CustomUser customUser) {
        CustomUser testCustomUser = ((UserRepository) repository).findByLogin(customUser.getLogin());
        if (testCustomUser != null) return true;
        return false;
    }

    public CustomUser getByLogin(String login) {
        return ((UserRepository) repository).findByLogin(login);
    }

    public void createNewCustomUserFromClientData(Client client) {
        var login = client.getFirstName() + client.getSecondName();
        var password = passwordEncoder.encode(client.getPhoneNumber());
        repository.save(new CustomUser(login, password, UserRole.ROLE_CLIENT, client.getEmail()));
    }
}