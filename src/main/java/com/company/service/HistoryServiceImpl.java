package com.company.service;

import com.company.db.BaseCardService;
import com.company.db.BaseHistoryService;
import com.company.model.TransactionField;
import com.company.model.TransactionType;
import com.company.model.entity.Card;
import com.company.model.entity.History;

import java.sql.SQLException;

import java.util.ArrayList;
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
        List<History> histories;
        if (getAllHistory() == null) {
            System.out.println("You do not have history of transactions");
            return;
        } else {
            histories = getAllHistory();
        }

        for (History history : histories) {
            System.out.println("\n------------------------");
            System.out.println("Card id : " + history.getCardId());
            System.out.println("Amount : " + history.getAmount());
            System.out.println("Transaction type : " + history.getTransactionType());
            System.out.println("Transaction field : " + history.getTransactionField());
            if (!Objects.equals(history.getTransactionObject(), "")) {
                System.out.println("Transaction object : " + history.getTransactionObject());
            }
            System.out.println("Transaction created : " + history.getCreatedAt());
            System.out.println("------------------------");
        }


    }

    @Override
    public void showCurrentCardHistory() throws SQLException {
        List<History> allHistory = getAllHistory();

        if (currentCard == null || currentCard.getCardId() == null) {
            System.out.println("Card is not selected");
            return;
        }

        if (allHistory.isEmpty()) {
            System.out.println("You do not have history of transactions");
            return;
        }

        for (History history : allHistory) {
            if (currentCard.getCardId().equals(history.getCardId())) {
                System.out.println("\n------------------------");
                System.out.println("Card id : " + history.getCardId());
                System.out.println("Amount : " + history.getAmount());
                System.out.println("Transaction type : " + history.getTransactionType());
                System.out.println("Transaction field : " + history.getTransactionField());
                if (!Objects.equals(history.getTransactionObject(), "")) {
                    System.out.println("Transaction object : " + history.getTransactionObject());
                }
                System.out.println("Transaction created : " + history.getCreatedAt());
                System.out.println("------------------------");
            }
        }

    }

    @Override
    public void showExpenses() throws SQLException {
        List<History> allHistory = getAllHistory();

        if (currentCard == null || currentCard.getCardId() == null) {
            System.out.println("Card is not selected");
            return;
        }

        if (allHistory.isEmpty()) {
            System.out.println("You do not have history of transactions");
            return;
        }

        for (History history : allHistory) {
            if (currentCard.getCardId().equals(history.getCardId()) && history.getTransactionType() == TransactionType.EXPENSE) {
                System.out.println("\n------------------------");
                System.out.println("Card id : " + history.getCardId());
                System.out.println("Amount : " + history.getAmount());
                System.out.println("Transaction type : " + history.getTransactionType());
                System.out.println("Transaction field : " + history.getTransactionField());
                if (!Objects.equals(history.getTransactionObject(), "")) {
                    System.out.println("Transaction object : " + history.getTransactionObject());
                }
                System.out.println("Transaction created : " + history.getCreatedAt());
                System.out.println("------------------------");
            }
        }
    }

    @Override
    public void showIncomes() throws SQLException {
        List<History> allHistory = getAllHistory();

        if (currentCard == null || currentCard.getCardId() == null) {
            System.out.println("Card is not selected");
            return;
        }

        if (allHistory.isEmpty()) {
            return;
        }

        for (History history : allHistory) {
            if (currentCard.getCardId().equals(history.getTransactionObject()) && history.getTransactionType() == TransactionType.EXPENSE) {
                System.out.println("\n------------------------");
                System.out.println("Card id : " + history.getTransactionObject());
                System.out.println("Amount : " + history.getAmount());
                System.out.println("Transaction type : " + TransactionType.INCOME);
                System.out.println("Transaction field : " + history.getTransactionField());
                System.out.println("Sender card id : " + history.getCardId());
                System.out.println("Transaction created : " + history.getCreatedAt());
                System.out.println("------------------------");
            }
        }
    }

    @Override
    public void showTransactions() throws SQLException {
        List<History> allHistory = getAllHistory();

        if (currentCard == null || currentCard.getCardId() == null) {
            System.out.println("Card is not selected");
            return;
        }

        if (allHistory.isEmpty()) {
            return;
        }

        for (History history : allHistory) {
            if (currentCard.getCardId().equals(history.getCardId()) && history.getTransactionField() == TransactionField.TRANSFER) {
                System.out.println("\n------------------------");
                System.out.println("Card id : " + history.getCardId());
                System.out.println("Amount : " + history.getAmount());
                System.out.println("Transaction type : " + TransactionType.EXPENSE);
                System.out.println("Transaction field : " + history.getTransactionField());
                System.out.println("Transaction object : " + history.getTransactionObject());
                System.out.println("Transaction created : " + history.getCreatedAt());
                System.out.println("------------------------");
            }

            if (currentCard.getCardId().equals(history.getTransactionObject()) && history.getTransactionField() == TransactionField.TRANSFER) {
                System.out.println("\n------------------------");
                System.out.println("Sender card id : " + history.getCardId());
                System.out.println("Amount : " + history.getAmount());
                System.out.println("Transaction type : " + TransactionType.INCOME);
                System.out.println("Transaction field : " + history.getTransactionField());
                System.out.println("Receiver card id : " + history.getTransactionObject());
                System.out.println("Transaction created : " + history.getCreatedAt());
                System.out.println("------------------------");
            }
        }
    }

    private List<History> getAllHistory() throws SQLException {
        List<Card> cardList = cardService.getCardByUserId(currentUser.getId());

        if (cardList.isEmpty()) {
            System.out.println("You do not have any cards");
            return null;
        }

        List<String> cardIds = cardList.stream()
                .map(Card::getCardId)
                .collect(Collectors.toList());

        return baseHistoryService.getAllHistory(cardIds);
    }
}
