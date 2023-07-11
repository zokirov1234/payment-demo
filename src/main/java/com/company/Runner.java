package com.company;

import com.company.db.BaseUserServiceImpl;
import com.company.service.AuthService;
import com.company.service.AuthServiceImpl;

import static com.company.utils.Helper.scannerInt;

public class Runner {

    private final AuthService authService;

    public Runner(AuthService authService) {
        this.authService = authService;
    }

    public void initialMenu() {
        System.out.println("\t\t\t\t---  Welcome to payment system  ---");
        int option = -1;

        while (option != 0) {
            System.out.println("\n1.Login");
            System.out.println("2.Register");
            System.out.println("3.Call center");
            System.out.println("0.Exit");
            System.out.print("Enter option : ");
            option = scannerInt.nextInt();
            System.out.println();
            switch (option) {
                case 1 :
                    System.out.println("d");
                    break;
                case 2 :
                    System.out.println("\t\t\tRegister");
                    System.out.println(authService.doRegister());
                    break;
                case 3:
                    System.out.println("asd");
                    break;
                case 0:
                    System.out.println("Good bye");
                    break;
            }

        }

    }
}
