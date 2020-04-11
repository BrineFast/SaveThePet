package com.savethepet.service;

import com.savethepet.model.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPageService {

    @Autowired
    UserRepo userRepo;

}
