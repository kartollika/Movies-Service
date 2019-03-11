package com.pau_pau.project;

import com.pau_pau.project.database.AccountsRepository;
import com.pau_pau.project.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExampleController {

    @Autowired
    private AccountsRepository accountsRepository;

    @PostMapping("/")
    @ResponseBody
    public String welcome(@RequestBody Account account) {

        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));

        accountsRepository.save(account);
        return "Done";
    }

    @RequestMapping("/accounts")
    @ResponseBody
    public List<Account> getAccounts() {
        return accountsRepository.findAll();
    }
}
