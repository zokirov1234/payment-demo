package com.company.service;

import java.sql.SQLException;

public interface UserService {

    void getMyAccountDetails();

    void callCenter() throws SQLException;

    void changePassword() throws SQLException;
}
