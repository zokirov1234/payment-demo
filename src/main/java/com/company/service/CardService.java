package com.company.service;

import com.company.model.dao.CardDao;
import com.company.model.entity.Card;

import java.sql.SQLException;

public interface CardService {

    Card generateCard();

    void getMyCards(int userId) throws SQLException;

    void changeCard() throws SQLException;
}
