package com.pau_pau.project;

import com.pau_pau.project.database.AccountsRepository;
import com.pau_pau.project.models.Account;
import com.pau_pau.project.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExampleController {

    @Autowired
    private AccountsRepository accountsRepository;

    @GetMapping("/")
    @ResponseBody
    public String welcome() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "Done";
    }

    @RequestMapping("/accounts")
    @ResponseBody

    public List<Account> getAccounts() {

        return accountsRepository.findAll();
    }
}
