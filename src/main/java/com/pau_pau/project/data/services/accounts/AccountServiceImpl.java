package com.pau_pau.project.data.services.accounts;

import com.pau_pau.project.common.utils.PasswordEncoderUtil;
import com.pau_pau.project.data.repository.accounts.AccountsRepository;
import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;
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
    public void updateRole(String username, Role role) throws Exception {
        Account updatingAccount = accountsRepository.findByUsername(username).orElseThrow(Exception::new);
        updatingAccount.setPermissionsLevel(role);
        accountsRepository.save(updatingAccount);
    }

    @Override
    public Account findByUsername(String username) throws Exception {
        return accountsRepository.findByUsername(username).orElseThrow(Exception::new);
    }
}
