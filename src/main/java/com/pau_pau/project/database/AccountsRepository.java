package com.pau_pau.project.database;

import com.pau_pau.project.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);

    Account findById(int id);
}
