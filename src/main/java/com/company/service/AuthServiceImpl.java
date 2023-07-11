package com.company.service;


import com.company.db.BaseUserService;
import com.company.model.dao.UserDao;
import lombok.AllArgsConstructor;

import java.sql.SQLException;

import static com.company.utils.Helper.scannerStr;

public class AuthServiceImpl implements AuthService {

    private final BaseUserService baseUserService;

    public AuthServiceImpl(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }


    @Override
    public String doRegister() {

        System.out.print("Enter your name : ");
        String name = scannerStr.nextLine().trim();

        if (name.length() < 1) {
            return "name length must be greater than 0";
        }

        System.out.print("Enter your phone number : ");
        String phoneNumber = scannerStr.nextLine().trim();

        if (phoneNumber.length() != 9 && phoneNumber.length() != 12 && phoneNumber.length() != 13) {
            return "phone number length must be 9 or 12 or 13";
        }

        //check phone number is exists

        System.out.print("Enter your password : ");
        String password = scannerStr.nextLine();

        if (password.length() < 1) {
            return "password length must greater than 0";
        }

        System.out.print("Confirm your password : ");
        String confirmPassword = scannerStr.nextLine();

        if (!confirmPassword.equals(password)) {
            return "confirm password is not accepted";
        }

        System.out.print("Enter your secret word : ");
        String secretWord = scannerStr.nextLine();

        if (phoneNumber.length() == 9) {
            phoneNumber = "+998" + phoneNumber;
        } else if (phoneNumber.length() == 12) {
            phoneNumber = "+" + phoneNumber;
        }

        try {
            boolean userByPhoneNumber = baseUserService.getUserByPhoneNumber(phoneNumber);
            if (userByPhoneNumber) {
                return "this phone already registered";
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        boolean res;
        try {
            res = baseUserService.addUser(new UserDao(name, phoneNumber, password, secretWord));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(res);

        return "you have been successfully registered";
    }
}
