package com.pau_pau.project.services;

import com.pau_pau.project.database.AccountsRepository;
import com.pau_pau.project.models.Account;
import com.pau_pau.project.models.Role;
import com.pau_pau.project.utils.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    @Override
    public void save(Account account) {
        account.setPassword(passwordEncoderUtil.passwordEncoder().encode(account.getPassword()));
        accountsRepository.save(account);
    }

    @Override
    public void updateRole(String username, Role role) {
        Account updatingAccount = accountsRepository.findByUsername(username);
        updatingAccount.setPermissionsLevel(role);
        accountsRepository.save(updatingAccount);
    }

    @Override
    public Account findByUsername(String username) {
        return accountsRepository.findByUsername(username);
    }

    @Override
    public Account findById(int id) {
        return accountsRepository.findById(id);
    }

    // Если найдем способ получше, то будет здорово
    private Account createNewAccountModelToSave(String oldUsername, Account oldAccountInfo, Account newAccountInfo) {
        Account newAccountModel = new Account();

        newAccountModel.setId(oldAccountInfo.getId());

        if (newAccountInfo.getName() == null) {
            newAccountModel.setName(oldAccountInfo.getName());
        } else {
            newAccountModel.setName(newAccountInfo.getName());
        }

        if (newAccountInfo.getUsername() == null) {
            newAccountModel.setUsername(oldUsername);
        } else {
            newAccountModel.setUsername(newAccountInfo.getUsername());
        }

        if (newAccountInfo.getPassword() == null) {
            newAccountModel.setPassword(oldAccountInfo.getPassword());
        } else {
            newAccountModel.setPassword(passwordEncoderUtil.passwordEncoder().encode(newAccountInfo.getPassword()));
        }

        if (newAccountInfo.getPermissionsLevel() == null) {
            newAccountModel.setPermissionsLevel(oldAccountInfo.getPermissionsLevel());
        } else {
            newAccountModel.setPermissionsLevel(newAccountInfo.getPermissionsLevel());
        }
        return newAccountModel;
    }
}
