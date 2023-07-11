package com.company.model.entity;

import com.company.model.TransactionField;
import com.company.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class History {

    private int id;
    private String cardId;
    private double amount;
    private TransactionType transactionType;
    private TransactionField transactionField;
    private String transactionObject;
    private Timestamp createdAt;
}
