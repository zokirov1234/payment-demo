package com.company.db;

import com.company.model.dao.TransactionDao;
import com.company.utils.Base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseTransactionServiceImpl implements BaseTransactionService, Base {
    @Override
    public boolean createTransaction(TransactionDao transactionDao) throws SQLException {
        final Connection connection = getConnection();
        final PreparedStatement preparedStatement
                = connection.prepareStatement("insert into transaction(sender_card, receiver_card, amount) values(?,?,?)");

        preparedStatement.setString(1, transactionDao.getSenderId());
        preparedStatement.setString(2, transactionDao.getReceiverId());
        preparedStatement.setDouble(3, transactionDao.getAmount());
        int i = preparedStatement.executeUpdate();
        return i > 0;
    }
}
