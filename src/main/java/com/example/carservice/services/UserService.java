package com.example.carservice.services;

import com.example.carservice.entity.User;
import com.example.carservice.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service

public class UserService extends EntityService<User> {
    protected UserService(UserRepository repository) {
        super(repository);
    }

    @Override
    protected Map<String, Function<String, List<User>>> setSearchFieldsAndCorrespondingMethods() {
        methodMap.put("Role", ((UserRepository) repository)::findAllByUserRoleContaining);
        return methodMap;
    }

    public User findByLoginOrEmail(String login) {
        return  ((UserRepository) repository).findByLogin(login);
    }

    public boolean isUserExist(User user) {
        User testUser = ((UserRepository) repository).findByLoginOrEmail(user.getLogin(), user.getEmail());
        if (testUser != null) return true;
        return false;
    }
}