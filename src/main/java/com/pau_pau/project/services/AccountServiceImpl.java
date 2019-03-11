package com.pau_pau.project.services;

import com.pau_pau.project.database.AccountsRepository;
import com.pau_pau.project.models.Account;
import com.pau_pau.project.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountsRepository accountsRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Account account) {
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        // everyone is user after registration
        account.setPermissionsLevel(Role.USER);
        accountsRepository.save(account);
    }

    @Override
    public Account findByUsername(String username) {
        return accountsRepository.findByUsername(username);
    }

    @Override
    public Account findById(int id) {
        return accountsRepository.findById(id);
    }
}
