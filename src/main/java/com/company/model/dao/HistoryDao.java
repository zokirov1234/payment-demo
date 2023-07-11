package com.company.model.dao;

import com.company.model.TransactionField;
import com.company.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDao {

    private String cardId;
    private double amount;
    private TransactionType transactionType;
    private TransactionField transactionField;
    private String transactionObject;
}
