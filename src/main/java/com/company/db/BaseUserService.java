package com.company.db;

import com.company.model.dao.UserDao;
import com.company.model.entity.User;

import java.sql.SQLException;

public interface BaseUserService {

    boolean addUser(UserDao userDao) throws SQLException;

    boolean getUserByPhoneNumber(String phoneNumber) throws SQLException;

    User getUserByPhoneAndPassword(String phoneNumber, String password) throws SQLException;

    boolean blockUserByPhoneNumber(String phoneNumber) throws SQLException;

    User getUserByPhoneNumberAndSecretWord(String phoneNumber, String secretWord) throws SQLException;

    boolean unblockUser(String phoneNumber, String password) throws SQLException;
}
