package com.company.service;

import com.company.db.BaseCardService;
import com.company.db.BaseHistoryService;
import com.company.db.BaseTransactionService;
import com.company.model.TransactionField;
import com.company.model.TransactionType;
import com.company.model.dao.HistoryDao;
import com.company.model.dao.TransactionDao;
import com.company.model.entity.Card;

import java.sql.SQLException;

import static com.company.Runner.currentCard;
import static com.company.Runner.currentUser;
import static com.company.utils.Helper.scannerStr;

public class TransactionServiceImpl implements TransactionService {

    private final BaseCardService baseCardService;
    private final BaseTransactionService baseTransactionService;

    private final BaseHistoryService baseHistoryService;

    public TransactionServiceImpl(BaseCardService baseCardService, BaseTransactionService baseTransactionService, BaseHistoryService baseHistoryService) {
        this.baseCardService = baseCardService;
        this.baseTransactionService = baseTransactionService;
        this.baseHistoryService = baseHistoryService;
    }

    @Override
    public void transfer() throws SQLException {
        System.out.println("\n\t\t\t\t --- Transfer ---");
        if (currentCard == null) {
            System.out.println("There is no card");
            return;
        }

        System.out.print("Enter card id : ");
        String cardId = scannerStr.nextLine();


        Card card = baseCardService.getCardByCardId(cardId);

        if (card == null || card.getCardId() == null) {
            System.out.println("There is no card with this id");
            return;
        }

        if (card.getCardId().equals(currentCard.getCardId())) {
            System.out.println("You can not transfer money your current card");
            return;
        }

        System.out.print("Enter amount : ");
        String balance = scannerStr.nextLine();
        double balanced = 0;
        try {
            balanced = Double.parseDouble(balance);
        } catch (Exception e) {
            System.out.println("Wrong type of amount");
            return;
        }

        if (balanced > currentCard.getBalance()) {
            System.out.println("There is no sufficient amount of money");
            return;
        }

        boolean transaction = baseTransactionService.createTransaction(new TransactionDao(currentCard.getCardId(), card.getCardId(), balanced));
        boolean b2 = baseHistoryService.addTransaction(new HistoryDao(currentCard.getCardId(),
                balanced, TransactionType.EXPENSE, TransactionField.TRANSFER, card.getCardId()));
        if (transaction && b2) {


            boolean b = baseCardService.changeBalanceByCardId(cardId, (card.getBalance() + balanced));

            if (!b) {
                System.out.println("Something went wrong");
                return;
            }
            currentCard.setBalance(currentCard.getBalance() - balanced);
            boolean b1 = baseCardService.changeBalanceByCardId(currentCard.getCardId(), currentCard.getBalance());

            if (b1) {
                System.out.println("Successfully transferred");
                return;
            }
            currentCard.setBalance(currentCard.getBalance() + balanced);
            System.out.println("Something went wrong");
            return;
        }
        System.out.println("Something went wrong");
    }
}
