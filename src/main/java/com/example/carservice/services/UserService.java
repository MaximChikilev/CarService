package com.example.carservice.services;

import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.entity.Client;
import com.example.carservice.entity.CustomUser;
import com.example.carservice.entity.UserRole;
import com.example.carservice.mail.MessageSender;
import com.example.carservice.mail.NewClientMessageGenerator;
import com.example.carservice.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class UserService extends EntityService<CustomUser> {
  private final PasswordEncoder passwordEncoder;
  private final MessageSender messageSender;

  protected UserService(
      UserRepository repository, PasswordEncoder passwordEncoder, MessageSender messageSender) {
    super(repository);
    this.passwordEncoder = passwordEncoder;
    this.messageSender = messageSender;
  }

  @Transactional
  @Override
  public boolean isDataErrorPresent(CustomUser entity) {
    boolean result = false;
    CustomUser customUser = findByLogin(entity.getLogin());
    if ((customUser != null) && ((getId(entity) == null)||(!entity.getId().equals(customUser.getId())))) result = true;
    customUser = findByEmail(entity.getEmail());
    if ((customUser != null) && ((getId(entity) == null)||(!entity.getId().equals(customUser.getId())))) result = true;
    if (!Utils.isEmailCorrect(entity.getEmail())) result = true;
    return result;
  }

  @Override
  protected Map<String, Function<String, List<CustomUser>>>
      setSearchFieldsAndCorrespondingMethods() {
    methodMap.put("Role", ((UserRepository) repository)::findAllByUserRoleContaining);
    return methodMap;
  }

  @Transactional
  @Override
  public List<CustomUser> loadEntityListFromJson() {
    return null;
  }

  @Transactional
  @Override
  public List<ConnectionsWithOtherEntityDTO> getConnectionsWithOtherTables(Long id) {
    return null;
  }

  @Transactional
  public CustomUser findByLogin(String login) {
    return ((UserRepository) repository).findByLogin(login);
  }
  @Transactional
  public CustomUser findByEmail(String email) {
    return ((UserRepository) repository).findByEmail(email);
  }

  @Transactional
  public boolean isUserExist(CustomUser customUser) {
    CustomUser testCustomUser = ((UserRepository) repository).findByLogin(customUser.getLogin());
    if (testCustomUser != null) return true;
    return false;
  }

  @Transactional
  public CustomUser getByLogin(String login) {
    return ((UserRepository) repository).findByLogin(login);
  }

  @Transactional
  public void createNewCustomUserFromClientData(Client client) {
    var login = client.getFirstName() + client.getSecondName();
    var password = passwordEncoder.encode(client.getPhoneNumber());
    repository.save(new CustomUser(login, password, UserRole.ROLE_CLIENT, client.getEmail()));
    messageSender.SendMessage(new NewClientMessageGenerator().getMessage(client));
  }

  public boolean isPasswordCorrect(String password) {
    return password.length() >= 8;
  }

  @Override
  @Transactional
  public void save(CustomUser user) {
    if (!isUserExist(user)) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      repository.save(user);
    } else {
      CustomUser userFromDB = getByLogin(user.getLogin());
      if (user.getPassword() != "") {
        userFromDB.setPassword(passwordEncoder.encode(user.getPassword()));
      }
      userFromDB.setRole(user.getRole());
      userFromDB.setEmail(user.getEmail());
      repository.save(userFromDB);
    }
  }

  @Override
  public Long getId(CustomUser entity) {
    return entity.getId();
  }
}
