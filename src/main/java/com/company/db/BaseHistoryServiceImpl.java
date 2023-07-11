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
        final Connection connection = getConnection();
        final PreparedStatement preparedStatement
                = connection.prepareStatement("insert into history(card_id, amount, type, transaction_field, transaction_object) values(?,?,?,?,?)");

        preparedStatement.setString(1, historyDao.getCardId());
        preparedStatement.setDouble(2, historyDao.getAmount());
        preparedStatement.setString(3, String.valueOf(historyDao.getTransactionType()));
        preparedStatement.setString(4, String.valueOf(historyDao.getTransactionField()));
        preparedStatement.setString(5, historyDao.getTransactionObject());

        int i = preparedStatement.executeUpdate();

        connection.close();
        preparedStatement.close();
        return i > 0;
    }

    @Override
    public List<History> getAllHistory(List<String> cardIds) throws SQLException {
        List<History> list = new ArrayList<>();
        final Connection connection = getConnection();
        final PreparedStatement preparedStatement
                = connection.prepareStatement("select id, card_id, amount, type, transaction_field, transaction_object, created_at from history where card_id = any (?) or transaction_object = any (?)");

        preparedStatement.setArray(1, connection.createArrayOf("VARCHAR", cardIds.toArray()));
        preparedStatement.setArray(2, connection.createArrayOf("VARCHAR", cardIds.toArray()));
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String cardId = resultSet.getString("card_id");
            double amount = resultSet.getDouble("amount");
            String type = resultSet.getString("type");
            String transactionField = resultSet.getString("transaction_field");
            String transactionObject = resultSet.getString("transaction_object");
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            list.add(new History(id, cardId, amount, TransactionType.valueOf(type),
                    TransactionField.valueOf(transactionField), transactionObject, createdAt));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return list;
    }
}
