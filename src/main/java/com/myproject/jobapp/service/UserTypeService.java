package com.myproject.jobapp.service;

import com.myproject.jobapp.entity.UserType;
import com.myproject.jobapp.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public List<UserType> findAll() {
        return userTypeRepository.findAll();
    }
}
