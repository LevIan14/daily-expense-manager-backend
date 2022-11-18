package com.example.DEM.service;


import com.example.DEM.entity.UserEntity;
import com.example.DEM.model.MyUserDetail;
import com.example.DEM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService {

  @Autowired
  private UserRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    UserEntity user = usersRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("No user found!");
    }
    return new MyUserDetail(user);
  }
}

