package com.company.db;

import com.company.model.dao.HistoryDao;
import com.company.model.entity.Card;
import com.company.model.entity.History;

import java.sql.SQLException;
import java.util.List;

public interface BaseHistoryService {

    boolean addTransaction(HistoryDao historyDao) throws SQLException;

    List<History> getAllHistory(List<String> cardIds) throws SQLException;
}
