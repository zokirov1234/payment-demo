package com.company.service;

import java.sql.SQLException;

public interface HistoryService {

    void getHistory() throws SQLException;

    void showCurrentCardHistory() throws SQLException;

    void showExpenses() throws SQLException;

    void showIncomes() throws SQLException;

    void showTransactions() throws SQLException;
}
