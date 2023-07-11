package com.company.db;

import com.company.model.dao.CardDao;
import com.company.model.entity.Card;

import java.sql.SQLException;
import java.util.List;

public interface BaseCardService {

    int createCard(CardDao cardDao) throws SQLException;

    List<Card> getCardByUserId(int userId) throws SQLException;

    Card getCardByCardId(String cardId) throws SQLException;
}
