package com.company.db;

import com.company.model.dao.UserDao;

import java.sql.SQLException;

public interface BaseUserService {

    boolean addUser(UserDao userDao) throws SQLException;

    boolean getUserByPhoneNumber(String phoneNumber) throws SQLException;

}
