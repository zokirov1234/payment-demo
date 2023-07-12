package com.company.service;

import com.company.db.BaseCardService;
import com.company.db.BaseHistoryService;
import com.company.model.TransactionField;
import com.company.model.TransactionType;
import com.company.model.entity.Card;
import com.company.model.entity.History;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.company.Runner.currentCard;
import static com.company.Runner.currentUser;

public class HistoryServiceImpl implements HistoryService {

    private final BaseHistoryService baseHistoryService;
    private final BaseCardService cardService;

    public HistoryServiceImpl(BaseHistoryService baseHistoryService, BaseCardService cardService) {
        this.baseHistoryService = baseHistoryService;
        this.cardService = cardService;
    }

    @Override
    public void getHistory() throws SQLException {
        List<History> histories = getAllHistory();
        if (histories.isEmpty()) {
            System.out.println("You do not have any history of transactions");
            return;
        }
        for (History history : histories) {
            printHistory(history);
        }
    }

    @Override
    public void showCurrentCardHistory() throws SQLException {
        List<History> histories = getAllHistory();
        if (currentCard == null || currentCard.getCardId() == null) {
            System.out.println("Card is not selected");
            return;
        }
        if (histories.isEmpty()) {
            System.out.println("You do not have any history of transactions");
            return;
        }
        for (History history : histories) {
            if (currentCard.getCardId().equals(history.getCardId())) {
                printHistory(history);
            }
        }
    }

    @Override
    public void showExpenses() throws SQLException {
        List<History> histories = getAllHistory();
        if (currentCard == null || currentCard.getCardId() == null) {
            System.out.println("Card is not selected");
            return;
        }
        if (histories.isEmpty()) {
            System.out.println("You do not have any history of transactions");
            return;
        }
        for (History history : histories) {
            if (currentCard.getCardId().equals(history.getCardId()) && history.getTransactionType() == TransactionType.EXPENSE) {
                printHistory(history);
            }
        }
    }

    @Override
    public void showIncomes() throws SQLException {
        List<History> histories = getAllHistory();
        if (currentCard == null || currentCard.getCardId() == null) {
            System.out.println("Card is not selected");
            return;
        }
        if (histories.isEmpty()) {
            return;
        }
        for (History history : histories) {
            if (currentCard.getCardId().equals(history.getTransactionObject()) && history.getTransactionType() == TransactionType.EXPENSE) {
                printIncomeHistory(history);
            }
        }
    }

    @Override
    public void showTransactions() throws SQLException {
        List<History> histories = getAllHistory();
        if (currentCard == null || currentCard.getCardId() == null) {
            System.out.println("Card is not selected");
            return;
        }
        if (histories.isEmpty()) {
            return;
        }
        for (History history : histories) {
            if (currentCard.getCardId().equals(history.getCardId()) && history.getTransactionField() == TransactionField.TRANSFER) {
                printHistory(history);
            } else if (currentCard.getCardId().equals(history.getTransactionObject()) && history.getTransactionField() == TransactionField.TRANSFER) {
                printTransferHistory(history);
            }
        }
    }

    private List<History> getAllHistory() throws SQLException {
        List<Card> cardList = cardService.getCardByUserId(currentUser.getId());
        if (cardList.isEmpty()) {
            System.out.println("You do not have any cards");
            return List.of();
        }
        List<String> cardIds = cardList.stream()
                .map(Card::getCardId)
                .collect(Collectors.toList());
        return baseHistoryService.getAllHistory(cardIds);
    }

    private void printHistory(History history) {
        System.out.println("\n------------------------");
        System.out.println("Card ID: " + history.getCardId());
        System.out.println("Amount: " + history.getAmount());
        System.out.println("Transaction Type: " + history.getTransactionType());
        System.out.println("Transaction Field: " + history.getTransactionField());
        if (!Objects.equals(history.getTransactionObject(), "")) {
            System.out.println("Transaction Object: " + history.getTransactionObject());
        }
        System.out.println("Transaction Created: " + history.getCreatedAt());
        System.out.println("------------------------");
    }

    private void printIncomeHistory(History history) {
        System.out.println("\n------------------------");
        System.out.println("Card ID: " + history.getTransactionObject());
        System.out.println("Amount: " + history.getAmount());
        System.out.println("Transaction Type: " + TransactionType.INCOME);
        System.out.println("Transaction Field: " + history.getTransactionField());
        System.out.println("Sender Card ID: " + history.getCardId());
        System.out.println("Transaction Created: " + history.getCreatedAt());
        System.out.println("------------------------");
    }

    private void printTransferHistory(History history) {
        System.out.println("\n------------------------");
        System.out.println("Card ID: " + history.getCardId());
        System.out.println("Amount: " + history.getAmount());
        System.out.println("Transaction Type: " + TransactionType.EXPENSE);
        System.out.println("Transaction Field: " + history.getTransactionField());
        System.out.println("Transaction Object: " + history.getTransactionObject());
        System.out.println("Transaction Created: " + history.getCreatedAt());
        System.out.println("------------------------");
    }
}
