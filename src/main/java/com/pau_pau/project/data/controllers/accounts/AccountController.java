package com.pau_pau.project.data.controllers.accounts;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.data.models.accounts.Account;
import com.pau_pau.project.data.models.accounts.AccountDto;
import com.pau_pau.project.data.models.accounts.Role;
import com.pau_pau.project.data.services.accounts.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
@Api(tags = "Accounts", value = "Accounts", description = "Api for operations with accounts")
@RequestMapping(ControllerConstants.ACCOUNT_URL)

public class AccountController {

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "Get account information", response = AccountDto.class)
    @GetMapping(value = ControllerConstants.ACCOUNT_USERNAME, consumes = "application/json")
    public AccountDto getAccountInfo(@PathVariable Optional<String> username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = username.orElse(authentication.getName());
        try {
            return AccountDto.dtoFromAccount(accountService.findByUsername(name));

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

    }

    @ApiOperation(value = "Account registration")
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void registration(@RequestBody AccountDto accountDto) {
        accountDto.setPermissionsLevel(Role.USER);
        try {
            accountService.save(prepareAccountModelFromDtoWithRole(accountDto));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "Update account role")
    @PutMapping(value = ControllerConstants.CHANGE_ROLE, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
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
