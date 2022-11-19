package com.example.DEM.service;

import com.example.DEM.entity.User;
import com.example.DEM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getDetailUser(String username) {
        return userRepository.findByUsername(username);
    }
}
