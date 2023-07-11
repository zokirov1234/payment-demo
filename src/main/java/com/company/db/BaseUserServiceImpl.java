package com.company.db;

import com.company.model.dao.UserDao;
import com.company.utils.Base;

import java.sql.*;

public class BaseUserServiceImpl implements Base, BaseUserService {
    public boolean addUser(UserDao userDao) throws SQLException {
        final Connection connection = getConnection();
        final PreparedStatement preparedStatement
                = connection.prepareStatement("insert into users(name, phone_number, password, secret_key) values(?,?,?,?)");

        preparedStatement.setString(1, userDao.getName());
        preparedStatement.setString(2, userDao.getPhoneNumber());
        preparedStatement.setString(3, userDao.getPassword());
        preparedStatement.setString(4, userDao.getSecretWord());
        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
        return true;
    }

    public boolean getUserByPhoneNumber(String phoneNumber) throws SQLException {
        final Connection connection = getConnection();

        Statement statement = connection.createStatement();

        // Execute the query and obtain a ResultSet
        ResultSet resultSet = statement.executeQuery("SELECT name,phone_number FROM users where phone_number = '" + phoneNumber + "'");

        // Process the ResultSet
        while (resultSet.next()) {
            if (resultSet.getString("phone_number").equals(phoneNumber)) {
                return true;
            }
        }

        // Close the ResultSet, statement, and connection
        resultSet.close();
        statement.close();
        connection.close();
        return false;
    }
}
