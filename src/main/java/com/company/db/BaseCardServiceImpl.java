package com.company.db;

import com.company.model.dao.CardDao;
import com.company.model.entity.Card;
import com.company.utils.Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseCardServiceImpl implements Base, BaseCardService {
    @Override
    public int createCard(CardDao cardDao) throws SQLException {
        int generatedId = -1;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into cards(card_id, user_id, balance) values(?,?,?)", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, cardDao.getCardId());
            preparedStatement.setInt(2, cardDao.getUserId());
            preparedStatement.setDouble(3, cardDao.getBalance());

            int i = preparedStatement.executeUpdate();

            if (i > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    }
                }
            }
        }

        return generatedId;
    }

    @Override
    public List<Card> getCardByUserId(int userId) throws SQLException {
        List<Card> list = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select id, card_id, balance from cards where user_id = ?")) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Card card = new Card();
                    int id = resultSet.getInt("id");
                    String cardId = resultSet.getString("card_id");
                    double balance = resultSet.getDouble("balance");
                    card.setCardId(cardId);
                    card.setUserId(userId);
                    card.setBalance(balance);
                    card.setId(id);
                    list.add(card);
                }
            }
        }

        return list;
    }

    @Override
    public Card getCardByCardId(String cardId) throws SQLException {
        Card card = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select id, card_id, balance, user_id from cards where card_id = ?")) {

            preparedStatement.setString(1, cardId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    double balance = resultSet.getDouble("balance");
                    int userId = resultSet.getInt("user_id");

                    card = new Card();
                    card.setCardId(cardId);
                    card.setUserId(userId);
                    card.setBalance(balance);
                    card.setId(id);
                }
            }
        }

        return card;
    }

    @Override
    public Card getCardByCardIdAndUserId(String cardId, int userId) throws SQLException {
        Card card = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select id, card_id, balance, user_id from cards where card_id = ? and user_id = ?")) {

            preparedStatement.setString(1, cardId);
            preparedStatement.setInt(2, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    double balance = resultSet.getDouble("balance");

                    card = new Card();
                    card.setCardId(cardId);
                    card.setUserId(userId);
                    card.setBalance(balance);
                    card.setId(id);
                }
            }
        }

        return card;
    }

    @Override
    public boolean changeBalanceByCardId(String cardId, double balance) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update cards set balance = ? where card_id = ?")) {

            preparedStatement.setDouble(1, balance);
            preparedStatement.setString(2, cardId);

            int i = preparedStatement.executeUpdate();

            return i > 0;
        }
    }
}
