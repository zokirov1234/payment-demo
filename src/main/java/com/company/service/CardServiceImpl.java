package com.company.service;

import com.company.db.BaseCardService;
import com.company.db.BaseHistoryService;
import com.company.model.TransactionField;
import com.company.model.TransactionType;
import com.company.model.dao.CardDao;
import com.company.model.dao.HistoryDao;
import com.company.model.entity.Card;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import static com.company.Runner.currentCard;
import static com.company.Runner.currentUser;
import static com.company.utils.Helper.scannerStr;

public class CardServiceImpl implements CardService {

    private final BaseCardService baseCardService;
    private final BaseHistoryService baseHistoryService;

    public CardServiceImpl(BaseCardService baseCardService, BaseHistoryService baseHistoryService) {
        this.baseCardService = baseCardService;
        this.baseHistoryService = baseHistoryService;
    }

    @Override
    public Card generateCard() {
        System.out.print("Enter your secret word: ");
        String secretWord = scannerStr.nextLine();

        if (!secretWord.equals(currentUser.getSecretWord())) {
            System.out.println("Wrong secret word");
            return null;
        }

        String cardId = "8600" + generateNum() + generateNum() + generateNum();
        int userId = currentUser.getId();
        double balance = 100.0;

        CardDao cardDao = new CardDao(cardId, userId, balance);

        try {
            int generatedId = baseCardService.createCard(cardDao);
            if (generatedId > -1) {
                return new Card(generatedId, userId, cardId, balance);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Failed to generate card. Please try again.");
        return null;
    }

    @Override
    public void getMyCards(int userId) throws SQLException {
        List<Card> cardList = baseCardService.getCardByUserId(userId);
        double sumBalance = 0;

        System.out.println("\n\t\t--- Cards ---");
        for (Card card : cardList) {
            sumBalance += card.getBalance();
            System.out.println("---------------------");
            System.out.println("Card ID: " + card.getCardId());
            System.out.println("Balance: " + card.getBalance());
            System.out.println("---------------------");
        }
        System.out.println("Number of cards: " + cardList.size());
        System.out.println("Sum of your balance: " + sumBalance);
    }

    @Override
    public void changeCard() throws SQLException {
        if (currentCard != null) {
            System.out.println("Current card ID: " + currentCard.getCardId());
        }
        System.out.print("Enter your card ID: ");
        String cardId = scannerStr.nextLine();
        Card card = baseCardService.getCardByCardIdAndUserId(cardId, currentUser.getId());
        if (card.getCardId() == null) {
            System.out.println("Invalid card ID");
            return;
        }
        currentCard = card;
        System.out.println("Card selected");
    }

    @Override
    public void converter() throws SQLException {
        System.out.println("\n\t\t\t\t--- Converter ---");

        if (currentCard == null || currentCard.getCardId() == null) {
            System.out.println("There is no card selected");
            return;
        }

        System.out.println("Your money in foreign currencies");
        System.out.println("Your money in ruble: " + (currentCard.getBalance() / 126.62));
        System.out.println("Your money in USD: " + (currentCard.getBalance() / 11505.72));
        System.out.println("Your money in Euro: " + (currentCard.getBalance() / 12672.23));
    }

    @Override
    public void withdraw() throws SQLException {
        System.out.println("\n\t\t\t\t--- Withdraw ---");

        if (currentCard == null || currentCard.getCardId() == null) {
            System.out.println("No card is selected");
            return;
        }

        System.out.print("Enter the amount: ");
        String amount = scannerStr.nextLine();
        double balance = 0;

        try {
            balance = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount");
            return;
        }

        if (balance > currentCard.getBalance()) {
            System.out.println("Insufficient balance");
            return;
        }

        boolean isSuccess = baseHistoryService.addTransaction(new HistoryDao(
                currentCard.getCardId(), balance, TransactionType.EXPENSE, TransactionField.WITHDRAW, ""));

        if (!isSuccess) {
            System.out.println("Failed to record the transaction");
            return;
        }

        currentCard.setBalance(currentCard.getBalance() - balance);
        boolean isBalanceUpdated = baseCardService.changeBalanceByCardId(currentCard.getCardId(), currentCard.getBalance());

        if (isBalanceUpdated) {
            System.out.println("Withdrawal successful");
        } else {
            System.out.println("Failed to update the card balance");
        }
    }

    private String generateNum() {
        Random random = new Random();
        int randomNumber = random.nextInt(9001) + 999;
        return String.valueOf(randomNumber);
    }
}
