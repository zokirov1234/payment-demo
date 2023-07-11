package com.company;

import com.company.db.BaseUserServiceImpl;
import com.company.service.AuthServiceImpl;

public class Main {

    public static void main(String[] args) {

        Runner runner = new Runner(new AuthServiceImpl(new BaseUserServiceImpl()));

        runner.initialMenu();
    }
}