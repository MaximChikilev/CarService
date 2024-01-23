package com.example.carservice.services;

import com.example.carservice.entity.CustomUser;
import com.example.carservice.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service

public class UserService extends EntityService<CustomUser> {
    protected UserService(UserRepository repository) {
        super(repository);
    }

    @Override
    protected Map<String, Function<String, List<CustomUser>>> setSearchFieldsAndCorrespondingMethods() {
        methodMap.put("Role", ((UserRepository) repository)::findAllByUserRoleContaining);
        return methodMap;
    }

    public CustomUser findByLoginOrEmail(String login) {
        return  ((UserRepository) repository).findByLogin(login);
    }

    public boolean isUserExist(CustomUser customUser) {
        CustomUser testCustomUser = ((UserRepository) repository).findByLogin(customUser.getLogin());
        if (testCustomUser != null) return true;
        return false;
    }
    public CustomUser getByLogin(String login){
        return  ((UserRepository) repository).findByLogin(login);
    }
}