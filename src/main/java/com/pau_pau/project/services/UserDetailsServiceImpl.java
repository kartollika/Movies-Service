package com.pau_pau.project.services;

import com.pau_pau.project.database.AccountsRepository;
import com.pau_pau.project.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountsRepository.findByUsername(username);

        if (!account.isPresent()) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        Account presentAccount = account.get();

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(presentAccount.getPassword())
                .roles(presentAccount.getPermissionsLevel().name())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();


    }

}
