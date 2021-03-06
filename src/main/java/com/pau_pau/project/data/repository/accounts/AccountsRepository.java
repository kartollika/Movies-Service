package com.pau_pau.project.data.repository.accounts;

import com.pau_pau.project.models.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);

    Optional<Account> findById(int id);
}
