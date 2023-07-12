package com.company.db;

import com.company.model.dao.TransactionDao;
import com.company.utils.Base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaseTransactionServiceImpl implements BaseTransactionService, Base {

    @Override
    public boolean createTransaction(TransactionDao transactionDao) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into transaction(sender_card, receiver_card, amount) values(?,?,?)")) {

            preparedStatement.setString(1, transactionDao.getSenderId());
            preparedStatement.setString(2, transactionDao.getReceiverId());
            preparedStatement.setDouble(3, transactionDao.getAmount());

            int i = preparedStatement.executeUpdate();
            return i > 0;
        }
    }
}
