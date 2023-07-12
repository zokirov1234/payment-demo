package com.company.service;

import com.company.db.BaseUserService;
import com.company.model.entity.User;

import java.sql.SQLException;
import java.util.Random;

import static com.company.Runner.currentUser;
import static com.company.utils.Helper.scannerStr;

public class UserServiceImpl implements UserService {

    private final BaseUserService baseUserService;

    public UserServiceImpl(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

    @Override
    public void getMyAccountDetails() {


        System.out.println("\nname : " + currentUser.getName());
        System.out.println("phone number : " + currentUser.getPhoneNumber());
    }

    @Override
    public void callCenter() throws SQLException {
        System.out.print("Enter your phone number : ");
        String phoneNumber = scannerStr.nextLine();
        System.out.print("Enter your secret word : ");
        String secretWord = scannerStr.nextLine();

        if (phoneNumber.length() == 9) {
            phoneNumber = "+998" + phoneNumber;
        } else if (phoneNumber.length() == 12) {
            phoneNumber = "+" + phoneNumber;
        }

        User user = baseUserService.getUserByPhoneNumberAndSecretWord(phoneNumber, secretWord);

        if (user == null || user.getPhoneNumber() == null) {
            System.out.println("Wrong phone number or secret word");
            return;
        }

        Random random = new Random();
        int randomNumber = random.nextInt(999);

        String password = String.valueOf(randomNumber);

        boolean isExecuted = baseUserService.unblockUser(phoneNumber, password);

        if (isExecuted) {
            System.out.println("Your account has been unblocked.");
            System.out.println("Your new password : " + password);
            return;
        }

        System.out.println("Something went wrong.Try again");
    }

    @Override
    public void changePassword() throws SQLException {
        System.out.print("\nEnter your old password : ");
        String oldPassword = scannerStr.nextLine();

        User user = baseUserService.getUserByPhoneAndPassword(currentUser.getPhoneNumber(), oldPassword);

        if (user == null || user.getPhoneNumber() == null) {
            System.out.println("Wrong password");
            return;
        }

        System.out.print("Enter new password : ");
        String newPassword = scannerStr.nextLine();

        if (newPassword.length()<1) {
            System.out.println("Password length greater than 0");
            return;
        }

        if (baseUserService.unblockUser(currentUser.getPhoneNumber(), newPassword)){
            System.out.println("Password change successfully");
            return;
        }

        System.out.println("Something went wrong");
    }

}
