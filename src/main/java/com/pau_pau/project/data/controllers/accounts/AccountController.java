package com.pau_pau.project.data.controllers.accounts;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.models.Account;
import com.pau_pau.project.models.AccountDto;
import com.pau_pau.project.models.Role;
import com.pau_pau.project.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
@RequestMapping(ControllerConstants.ACCOUNT_URL)

public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value = ControllerConstants.ACCOUNT_USERNAME, consumes = "application/json")
    public AccountDto getAccountInfo(@PathVariable Optional<String> username,
                                     Authentication authentication) {
        String name = username.orElse(authentication.getName());
        try {
            return AccountDto.dtoFromAccount(accountService.findByUsername(name));

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

    }

    @PostMapping(consumes = "application/json")
    public void registration(@RequestBody AccountDto accountDto) {
        accountDto.setPermissionsLevel(Role.USER);
        try {
            accountService.save(prepareAccountModelFromDtoWithRole(accountDto));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping(value = ControllerConstants.CHANGE_ROLE, consumes = "application/json")
    public void updateAccountRole(@PathVariable String username,
                                  @RequestParam Role newRole) {
        try {
            accountService.updateRole(username, newRole);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

    }

    private Account prepareAccountModelFromDtoWithRole(AccountDto accountDto) {
        Account account = new Account();
        account.setName(accountDto.getName());
        account.setUsername(accountDto.getUsername());
        account.setPassword(accountDto.getPassword());
        account.setPermissionsLevel(accountDto.getPermissionsLevel());
        return account;

    }


}
