package com.company.db;

import com.company.model.TransactionField;
import com.company.model.TransactionType;
import com.company.model.dao.HistoryDao;
import com.company.model.entity.History;
import com.company.utils.Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseHistoryServiceImpl implements BaseHistoryService, Base {

    @Override
    public boolean addTransaction(HistoryDao historyDao) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into history(card_id, amount, type, transaction_field, transaction_object) values(?,?,?,?,?)")) {

            preparedStatement.setString(1, historyDao.getCardId());
            preparedStatement.setDouble(2, historyDao.getAmount());
            preparedStatement.setString(3, historyDao.getTransactionType().name());
            preparedStatement.setString(4, historyDao.getTransactionField().name());
            preparedStatement.setString(5, historyDao.getTransactionObject());

            int i = preparedStatement.executeUpdate();
            return i > 0;
        }
    }

    @Override
    public List<History> getAllHistory(List<String> cardIds) throws SQLException {
        List<History> list = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select id, card_id, amount, type, transaction_field, transaction_object, created_at from history where card_id = any (?) or transaction_object = any (?)")) {

            Array cardIdsArray = connection.createArrayOf("VARCHAR", cardIds.toArray());

            preparedStatement.setArray(1, cardIdsArray);
            preparedStatement.setArray(2, cardIdsArray);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String cardId = resultSet.getString("card_id");
                    double amount = resultSet.getDouble("amount");
                    TransactionType type = TransactionType.valueOf(resultSet.getString("type"));
                    TransactionField transactionField = TransactionField.valueOf(resultSet.getString("transaction_field"));
                    String transactionObject = resultSet.getString("transaction_object");
                    Timestamp createdAt = resultSet.getTimestamp("created_at");

                    History history = new History(id, cardId, amount, type, transactionField, transactionObject, createdAt);
                    list.add(history);
                }
            }
        }

        return list;
    }
}
