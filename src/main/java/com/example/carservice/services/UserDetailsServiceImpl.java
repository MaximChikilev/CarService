package com.example.carservice.services;

import com.example.carservice.entity.CustomUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserService userService;

  public UserDetailsServiceImpl(UserService userService) {
    this.userService = userService;
  }

  @Transactional
  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    CustomUser customUser = userService.findByLogin(login);
    if (customUser == null) throw new UsernameNotFoundException(login + " not found");

    List<GrantedAuthority> roles =
        List.of(new SimpleGrantedAuthority(customUser.getRole().toString()));

    return new User(customUser.getLogin(), customUser.getPassword(), roles);
  }
}
