package com.company;

import com.company.db.BaseCardServiceImpl;
import com.company.db.BaseUserServiceImpl;
import com.company.service.AuthServiceImpl;
import com.company.service.CardServiceImpl;
import com.company.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {

        Runner runner = new Runner(new AuthServiceImpl(new BaseUserServiceImpl()),
                new UserServiceImpl(new BaseCardServiceImpl()),
                new CardServiceImpl(new BaseCardServiceImpl()));

        runner.initialMenu();
    }
}