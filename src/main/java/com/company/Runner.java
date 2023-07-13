package com.company;

import com.company.model.entity.Card;
import com.company.model.entity.User;
import com.company.service.*;

import java.sql.SQLException;

import static com.company.utils.Helper.scannerStr;

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

    public void initialMenu() throws SQLException {
        System.out.println("\t\t\t\t--- Welcome to the Payment System ---");
        String option = "";

        while (!option.equals("0")) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Call Center");
            System.out.println("0. Exit");
            System.out.print("Enter option: ");
            option = scannerStr.nextLine();
            System.out.println();
            switch (option) {
                case "1" -> {
                    System.out.println("\t\t\tLogin");
                    if (authService.doLogin()) {
                        try {
                            userMenu();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                case "2" -> {
                    System.out.println("\t\t\tRegister");
                    System.out.println(authService.doRegister());
                }
                case "3" -> callCenter();
                case "0" -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option!");
            }
        }
    }

    public void userMenu() throws SQLException {
        String option = "";

        while (!option.equals("0")) {
            System.out.println("\n\t\t\t\t--- Menu ---");
            System.out.println("1. Account Details");
            System.out.println("2. Balance");
            System.out.println("3. Transfer");
            System.out.println("4. Converter");
            System.out.println("5. Withdraw");
            System.out.println("6. History");
            System.out.println("7. Card Section");
            System.out.println("0. Exit");
            System.out.print("Enter option: ");
            option = scannerStr.nextLine();

            switch (option) {
                case "1" -> accountDetails();
                case "2" -> {
                    if (currentCard == null) {
                        System.out.println("There is no card selected.");
                        break;
                    }
                    System.out.println("Balance: " + currentCard.getBalance());
                }
                case "3" -> transactionService.transfer();
                case "4" -> cardService.converter();
                case "5" -> cardService.withdraw();
                case "6" -> {
                    String option1 = "";
                    while (!option1.equals("0")) {
                        System.out.println("\n\t\t\t\t--- History ---");
                        System.out.println("1. Show All of My Cards' History");
                        System.out.println("2. Show Current Card's History");
                        System.out.println("3. Show Expenses");
                        System.out.println("4. Show Incomes");
                        System.out.println("5. Show Transactions");
                        System.out.println("0. Exit");
                        System.out.print("Enter option: ");
                        option1 = scannerStr.nextLine();

                        switch (option1) {
                            case "1" -> historyService.getHistory();
                            case "2" -> historyService.showCurrentCardHistory();
                            case "3" -> historyService.showExpenses();
                            case "4" -> historyService.showIncomes();
                            case "5" -> historyService.showTransactions();
                            default -> System.out.println("Invalid option!");
                        }
                    }
                }
                case "7" -> {
                    String opt = "";
                    while (!opt.equals("0")) {
                        System.out.println("\n\t\t\t\t--- Card Menu ---");
                        System.out.println("1. My Cards");
                        System.out.println("2. Create Card");
                        System.out.println("3. Set Card");
                        System.out.println("4. Get Current Card");
                        System.out.println("0. Exit");
                        System.out.print("Enter option: ");
                        opt = scannerStr.nextLine();

                        switch (opt) {
                            case "1" -> cardService.getMyCards(currentUser.getId());
                            case "2" -> {
                                Card card = cardService.generateCard();
                                if (card != null) {
                                    System.out.println("You have successfully created your card.");
                                    currentCard = card;
                                }
                            }
                            case "3" -> cardService.changeCard();
                            case "4" -> {
                                if (currentCard == null) {
                                    System.out.println("There is no card.");
                                    break;
                                }
                                System.out.println("\nCard ID: " + currentCard.getCardId());
                                System.out.println("Balance: " + currentCard.getBalance());
                            }
                            default -> System.out.println("Invalid option!");
                        }
                    }
                }
                case "0" -> {
                    currentCard = null;
                    currentUser = null;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    public void callCenter() throws SQLException {
        System.out.println("\n\t\t\t\t--- Welcome to the Call Center ---");
        System.out.print("Enter 1 to recover your account: ");
        String option = scannerStr.nextLine();
        if (option.equals("1")) {
            userService.callCenter();
            return;
        }
        System.out.println("Wrong option!");
    }

    public void accountDetails() throws SQLException {

        String option = "";

        while (!option.equals("0")) {
            System.out.println("\n\t\t\t--- My account ---");
            System.out.println("1.Account details");
            System.out.println("2.Change password");
            System.out.println("0.Exit");
            System.out.print("Enter option : ");
            option = scannerStr.nextLine();

            switch (option) {
                case "1" -> userService.getMyAccountDetails();
                case "2" -> userService.changePassword();
                default -> System.out.println("Invalid option!");
            }
        }
    }
}
