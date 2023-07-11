package com.company.service;

import com.company.db.BaseCardService;
import com.company.model.entity.Card;

import java.sql.SQLException;
import java.util.List;

import static com.company.Runner.currentUser;

public class UserServiceImpl implements UserService {

    private final BaseCardService baseCardService;

    public UserServiceImpl(BaseCardService baseCardService) {
        this.baseCardService = baseCardService;
    }

    @Override
    public void getMyAccountDetails() {

        System.out.println("\t\t\t- - - My account - - -");
        System.out.println("name : " + currentUser.getName());
        System.out.println("phone number : " + currentUser.getPhoneNumber());
    }
}
