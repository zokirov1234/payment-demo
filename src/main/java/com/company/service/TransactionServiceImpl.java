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

        System.out.print("Enter card id: ");
        String cardId = scannerStr.nextLine();

        Card card = baseCardService.getCardByCardId(cardId);

        if (card == null || card.getCardId() == null) {
            System.out.println("There is no card with this id");
            return;
        }

        if (card.getCardId().equals(currentCard.getCardId())) {
            System.out.println("You cannot transfer money to your own card");
            return;
        }

        System.out.print("Enter amount: ");
        String balanceStr = scannerStr.nextLine();
        double balance = 0;
        try {
            balance = Double.parseDouble(balanceStr);
        } catch (Exception e) {
            System.out.println("Invalid amount format");
            return;
        }

        if (balance > currentCard.getBalance()) {
            System.out.println("Insufficient funds");
            return;
        }

        boolean transactionCreated = baseTransactionService.createTransaction(new TransactionDao(currentCard.getCardId(), card.getCardId(), balance));
        boolean historyAdded = baseHistoryService.addTransaction(new HistoryDao(currentCard.getCardId(), balance, TransactionType.EXPENSE, TransactionField.TRANSFER, card.getCardId()));
        if (transactionCreated && historyAdded) {
            boolean balanceUpdated = baseCardService.changeBalanceByCardId(cardId, (card.getBalance() + balance));
            if (!balanceUpdated) {
                System.out.println("Something went wrong while updating balance");
                return;
            }
            currentCard.setBalance(currentCard.getBalance() - balance);
            boolean senderBalanceUpdated = baseCardService.changeBalanceByCardId(currentCard.getCardId(), currentCard.getBalance());
            if (senderBalanceUpdated) {
                System.out.println("Transfer successful");
                return;
            } else {
                currentCard.setBalance(currentCard.getBalance() + balance);
                System.out.println("Something went wrong while updating sender's balance");
                return;
            }
        }
        System.out.println("Something went wrong while creating transaction or adding history");
    }
}
