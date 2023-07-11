package com.company.service;

import com.company.db.BaseCardService;
import com.company.model.dao.CardDao;
import com.company.model.entity.Card;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import static com.company.Runner.currentCard;
import static com.company.Runner.currentUser;
import static com.company.utils.Helper.scannerStr;

public class CardServiceImpl implements CardService {

    private final BaseCardService baseCardService;

    public CardServiceImpl(BaseCardService baseCardService) {
        this.baseCardService = baseCardService;
    }

    @Override
    public Card generateCard() {


        System.out.print("Enter your secret word : ");
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
            int cardIdEx = baseCardService.createCard(cardDao);
            if (cardIdEx > -1) {
                return new Card(cardIdEx, userId, cardId, balance);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Something went wrong");
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
            System.out.println("Card id : " + card.getCardId());
            System.out.println("Balance : " + card.getBalance());
            System.out.println("---------------------");
        }
        System.out.println("Number of cards : " + cardList.size());
        System.out.println("Sum of your balance : " + sumBalance);
    }

    @Override
    public void changeCard() throws SQLException {
        if (currentCard != null) {
            System.out.println("Current card id : " + currentCard.getCardId());
        }
        System.out.print("\nEnter your card id : ");
        String cardId = scannerStr.nextLine();
        Card card = baseCardService.getCardByCardId(cardId);
        if (card.getCardId() == null) {
            System.out.println("Something went wrong");
            return;
        }
        currentCard = card;
        System.out.println("Changed");
    }

    private String generateNum() {

        Random random = new Random();
        int randomNumber = random.nextInt(9001) + 999;

        return String.valueOf(randomNumber);
    }
}
