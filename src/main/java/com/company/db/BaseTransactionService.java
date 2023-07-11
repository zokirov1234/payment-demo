package com.company.db;

import com.company.model.dao.TransactionDao;

import java.sql.SQLException;

public interface BaseTransactionService {

    boolean createTransaction(TransactionDao transactionDao) throws SQLException;
}
