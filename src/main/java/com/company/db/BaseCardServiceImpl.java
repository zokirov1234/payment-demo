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
        boolean isExecuted = false;
        int generatedId = -1;
        final Connection connection = getConnection();
        final PreparedStatement preparedStatement
                = connection.prepareStatement("insert into cards(card_id, user_id, balance) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);

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

        preparedStatement.close();
        connection.close();
        return generatedId;
    }

    @Override
    public List<Card> getCardByUserId(int userId) throws SQLException {
        List<Card> list = new ArrayList<>();
        final Connection connection = getConnection();
        final PreparedStatement preparedStatement
                = connection.prepareStatement("select id,card_id, balance from cards where user_id = ?");

        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();

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
        resultSet.close();
        preparedStatement.close();
        connection.close();

        return list;
    }

    @Override
    public Card getCardByCardId(String cardId) throws SQLException {
        Card card = new Card();
        final Connection connection = getConnection();
        final PreparedStatement preparedStatement
                = connection.prepareStatement("select id,card_id, balance,user_id from cards where card_id = ?");

        preparedStatement.setString(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String cardIds = resultSet.getString("card_id");
            double balance = resultSet.getDouble("balance");
            int userId = resultSet.getInt("user_id");
            card.setCardId(cardIds);
            card.setUserId(userId);
            card.setBalance(balance);
            card.setId(id);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();


        return card;
    }


}
