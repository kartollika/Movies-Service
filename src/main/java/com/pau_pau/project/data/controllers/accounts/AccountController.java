package com.pau_pau.project.data.controllers.accounts;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.models.Account;
import com.pau_pau.project.models.AccountDto;
import com.pau_pau.project.models.ChangeRoleRequest;
import com.pau_pau.project.models.Role;
import com.pau_pau.project.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ControllerConstants.ACCOUNT_URL)
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public AccountDto getAccountInfo(Authentication authentication) {
        return AccountDto.dtoFromAccount(accountService.findByUsername(authentication.getName()));
    }

    @PostMapping
    public void registration(@RequestBody AccountDto accountDto) {
        accountDto.setPermissionsLevel(Role.USER);
        accountService.save(prepareAccountModelFromDtoWithRole(accountDto));
    }

    @PutMapping(ControllerConstants.CHANGE_ROLE)
    public void updateAccountRole(@RequestBody ChangeRoleRequest request) {
        accountService.updateRole(request.getUsername(), request.getNewRole());
    }


    //TODO если это в первую очередь, то надо найти, как обновить данные юзера после обновление логина или пароля
    // если нет, то сделаем это потом.
   /* @PutMapping
    public void updateAccountInfo(@RequestBody AccountDto accountDto, Authentication authentication) {
        Account account = prepareAccountModelFromDtoWithRole(accountDto);
        accountService.update(account, authentication.getName());
    }*/

    private Account prepareAccountModelFromDtoWithRole(AccountDto accountDto) {
        Account account = new Account();
        account.setName(accountDto.getName());
        account.setUsername(accountDto.getUsername());
        account.setPassword(accountDto.getPassword());
        account.setPermissionsLevel(accountDto.getPermissionsLevel());
        return account;

    }


}
