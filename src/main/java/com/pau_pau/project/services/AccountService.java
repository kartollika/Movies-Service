package com.pau_pau.project.services;

import com.pau_pau.project.models.Account;
import com.pau_pau.project.models.Role;

public interface AccountService {

    void save(Account account);

    void updateRole(String username, Role role) throws Exception;

    Account findByUsername(String username) throws Exception;
}