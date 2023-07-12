package com.company.service;

import java.sql.SQLException;

public interface AuthService {

    String doRegister();

    boolean doLogin() throws SQLException;
}
