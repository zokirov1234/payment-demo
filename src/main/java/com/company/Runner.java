package com.company;

import com.company.model.entity.Card;
import com.company.model.entity.User;
import com.company.service.*;

import java.sql.SQLException;

import static com.company.utils.Helper.scannerInt;

public class Runner {

    public static User currentUser;
    public static Card currentCard;

    private final AuthService authService;
    private final UserService userService;

    private final CardService cardService;

    private final HistoryService historyService;

    private final TransactionService transactionService;

    public Runner(AuthService authService, UserService userService, CardService cardService, HistoryService historyService, TransactionService transactionService) {
        this.authService = authService;
        this.userService = userService;
        this.cardService = cardService;
        this.historyService = historyService;
        this.transactionService = transactionService;
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
                case 1:
                    System.out.println("\t\t\tLogin");
                    if (authService.doLogin()) {
                        try {
                            userMenu();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:
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


    public void userMenu() throws SQLException {

        int option = -1;

        while (option != 0) {
            System.out.println("\n\t\t\t\t--- Menu ----");
            System.out.println("1.Account details");
            System.out.println("2.Balance");
            System.out.println("3.Transfer");
            System.out.println("4.Converter");
            System.out.println("5.Withdraw");
            System.out.println("6.History");
            System.out.println("7.Card section");
            System.out.println("0.Exit");
            System.out.print("Enter option : ");
            option = scannerInt.nextInt();

            switch (option) {
                case 1:
                    userService.getMyAccountDetails();
                    break;
                case 2:
                    if (currentCard == null) {
                        System.out.println("There is no card selected");
                        break;
                    }
                    System.out.println("Balance : " + currentCard.getBalance());
                    break;
                case 3:
                    transactionService.transfer();
                    break;
                case 4:
                    cardService.converter();
                    break;
                case 5:
                    cardService.withdraw();
                    break;
                case 6:

                    break;
                case 7:
                    int option1 = -1;

                    while (option1 != 0) {
                        System.out.println("\n\t\t\t\t--- History ---");
                        System.out.println("1.Show all of my cards history");
                        System.out.println("2.Show current card history");
                        System.out.println("3.Show expenses");
                        System.out.println("4.Show incomes");
                        System.out.println("5.Show transactions");
                        System.out.println("0.Exit");
                        System.out.print("Enter option : ");
                        option1 = scannerInt.nextInt();

                        switch (option1) {
                            case 1:
                                historyService.getHistory();
                                break;
                            case 2:
                                historyService.showCurrentCardHistory();
                                break;
                            case 3:
                                historyService.showExpenses();
                                break;
                            case 4:
                                historyService.showIncomes();
                                break;
                            case 5:
                                historyService.showTransactions();
                                break;
                        }
                    }
                    break;
                case 8:

                    break;
                case 9:

                    int opt = -1;

                    while (opt != 0) {
                        System.out.println("\n\t\t\t\t--- Card menu ---");
                        System.out.println("1.My cards");
                        System.out.println("2.Create card");
                        System.out.println("3.Change card");
                        System.out.println("4.Get current card");
                        System.out.println("0.Exit");
                        System.out.print("Enter option : ");
                        opt = scannerInt.nextInt();

                        switch (opt) {
                            case 1:
                                cardService.getMyCards(currentUser.getId());
                                break;
                            case 2:
                                Card card = cardService.generateCard();
                                if (card != null) {
                                    System.out.println("You have been successfully created your card");
                                    currentCard = card;
                                }
                                break;
                            case 3:
                                cardService.changeCard();
                                break;
                            case 4:
                                if (currentCard == null) {
                                    System.out.println("There is no card");
                                    break;
                                }
                                System.out.println("\nCard id : " + currentCard.getCardId());
                                System.out.println("Balance : " + currentCard.getBalance());
                                break;
                        }

                    }
                    break;
                case 0:
                    currentCard = null;
                    currentUser = null;
                    break;
            }
        }
    }
}
