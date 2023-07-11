package com.company.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDao {
    private String name;
    private String phoneNumber;
    private String password;
    private String secretWord;
}
