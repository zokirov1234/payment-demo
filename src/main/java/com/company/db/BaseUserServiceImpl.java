package com.company.db;

import com.company.model.dao.UserDao;
import com.company.model.entity.User;
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


        ResultSet resultSet = statement.executeQuery("SELECT name,phone_number FROM users where phone_number = '" + phoneNumber + "'");


        while (resultSet.next()) {
            if (resultSet.getString("phone_number").equals(phoneNumber)) {
                return true;
            }
        }


        resultSet.close();
        statement.close();
        connection.close();
        return false;
    }

    @Override
    public User getUserByPhoneAndPassword(String phoneNumber, String password) throws SQLException {

        final Connection connection = getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,name,phone_number,password,secret_key FROM users where phone_number = ? and password = ?");

        preparedStatement.setString(1, phoneNumber);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        User user = new User();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int id = resultSet.getInt("id");
            String password1 = resultSet.getString("password");
            String secretKey = resultSet.getString("secret_key");
            String phoneNumber1 = resultSet.getString("phone_number");
//            currentUser = new User(id, name, phoneNumber1, password1, secretKey);
            user.setPassword(password1);
            user.setId(id);
            user.setName(name);
            user.setPhoneNumber(phoneNumber1);
            user.setSecretWord(secretKey);
            return user;
        }


        resultSet.close();
        preparedStatement.close();
        connection.close();
        return user;
    }
}
