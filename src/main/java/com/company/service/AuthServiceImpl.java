package com.company.service;

import com.company.db.BaseUserService;
import com.company.model.dao.UserDao;
import com.company.model.entity.User;

import java.sql.SQLException;

import static com.company.Runner.currentUser;
import static com.company.utils.Helper.scannerStr;

public class AuthServiceImpl implements AuthService {

    private final BaseUserService baseUserService;

    public AuthServiceImpl(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

    @Override
    public String doRegister() {
        System.out.print("Enter your name: ");
        String name = scannerStr.nextLine().trim();

        if (name.isEmpty()) {
            return "Name must not be empty";
        }

        System.out.print("Enter your phone number: ");
        String phoneNumber = scannerStr.nextLine().trim();

        if (phoneNumber.length() != 9 && phoneNumber.length() != 12 && phoneNumber.length() != 13) {
            return "Phone number length must be 9, 12, or 13";
        }

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
                return "This phone number is already registered";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Perform the registration
        try {
            boolean res = baseUserService.addUser(new UserDao(name, phoneNumber, password, secretWord));
            if (res) {
                return "You have been successfully registered";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "Failed to register. Please try again.";
    }

    @Override
    public boolean doLogin() throws SQLException {
        String phone = "";
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter your phone number: ");
            String phoneNumber = scannerStr.nextLine();

            if (phoneNumber.length() != 9 && phoneNumber.length() != 12 && phoneNumber.length() != 13) {
                System.out.println("Wrong type of phone number");
                return false;
            }

            System.out.print("Enter your password: ");
            String password = scannerStr.nextLine();

            if (phoneNumber.length() == 9) {
                phoneNumber = "+998" + phoneNumber;
            } else if (phoneNumber.length() == 12) {
                phoneNumber = "+" + phoneNumber;
            }
            phone = phoneNumber;

            try {
                User user = baseUserService.getUserByPhoneAndPassword(phoneNumber, password);
                if (user.getPhoneNumber() != null) {
                    if (!user.isActive()) {
                        System.out.println("Your account is blocked. You need to recover it");
                        return false;
                    }
                    currentUser = user;
                    return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Phone number or password is incorrect");
            System.out.println("You have " + (2 - i) + " attempts left");
        }

        if (baseUserService.blockUserByPhoneNumber(phone)) {
            System.out.println("Your account has been blocked");
            return false;
        }

        System.out.println("Failed to log in. Please try again.");
        return false;
    }
}
