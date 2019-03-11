package com.pau_pau.project.services;

import com.pau_pau.project.models.Account;

public interface AccountService {

    void save(Account account);

    Account findByUsername(String username);

    Account findById(int id);
}