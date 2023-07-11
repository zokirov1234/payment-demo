package com.company;

import com.company.db.BaseCardServiceImpl;
import com.company.db.BaseHistoryServiceImpl;
import com.company.db.BaseTransactionServiceImpl;
import com.company.db.BaseUserServiceImpl;
import com.company.service.*;

public class Main {

    public static void main(String[] args) {

        Runner runner = new Runner(new AuthServiceImpl(new BaseUserServiceImpl()),
                new UserServiceImpl(new BaseCardServiceImpl()),
                new CardServiceImpl(new BaseCardServiceImpl(), new BaseHistoryServiceImpl()),
                new HistoryServiceImpl(new BaseHistoryServiceImpl(), new BaseCardServiceImpl()),
                new TransactionServiceImpl(new BaseCardServiceImpl(), new BaseTransactionServiceImpl(), new BaseHistoryServiceImpl()));

        runner.initialMenu();
    }
}