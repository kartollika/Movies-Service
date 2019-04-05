package com.pau_pau.project.data.services.accounts;

import com.pau_pau.project.data.models.accounts.Account;
import com.pau_pau.project.data.models.accounts.Role;

public interface AccountService {

    void save(Account account);

    void updateRole(String username, Role role) throws Exception;

    Account findByUsername(String username) throws Exception;
}