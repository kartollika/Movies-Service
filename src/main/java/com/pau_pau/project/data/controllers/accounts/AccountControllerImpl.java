package com.pau_pau.project.data.controllers.accounts;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.data.models.Film;
import com.pau_pau.project.data.models.FilmDTO;
import com.pau_pau.project.models.Account;
import com.pau_pau.project.models.AccountDto;
import com.pau_pau.project.models.Role;
import com.pau_pau.project.services.AccountService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@Api(tags = "Accounts", value = "Accounts", description = "Api for operations with accounts")
@RequestMapping(ControllerConstants.ACCOUNT_URL)

public class AccountControllerImpl implements AccountController {

    @Autowired
    private AccountService accountService;

    /* ================================
             GET METHODS
    ================================== */

    public AccountDto getAccountInfoByAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return getAccountInfoByUsername(name);
    }

    public AccountDto getAccountInfoByUsername(@PathVariable String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            return AccountDto.dtoFromAccount(accountService.findByUsername(username));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    public List<FilmDTO> getWishlistByAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            return accountService
                    .findByUsername(username)
                    .getWishlist()
                    .stream()
                    .map(FilmDTO::fromFilmModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    /* ================================
                 POST METHODS
     ================================== */

    public AccountDto registration(@RequestBody AccountDto accountDto) {
        accountDto.setPermissionsLevel(Role.USER);
        try {
            Account savedAccountModel = accountService.save(prepareAccountModelFromDtoWithRole(accountDto));
            return AccountDto.dtoFromAccount(savedAccountModel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    /* ================================
                 PUT METHODS
     ================================== */

    public AccountDto updateAccountRole(@PathVariable String username,
                                        @RequestParam Role newRole) {
        try {
            Account account = accountService.updateRole(username, newRole);
            return AccountDto.dtoFromAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    public FilmDTO addToWishlistByAuthentication(@RequestParam int filmId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Film savedFilm = accountService.addToWishlist(username, filmId);
            return FilmDTO.fromFilmModel(savedFilm);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    /* ================================
                 DELETE METHODS
     ================================== */

    public FilmDTO deteleFromWishlistByAuthentication(@RequestParam int filmId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            return FilmDTO.fromFilmModel(
                    accountService.deleteFromWishlist(username, filmId));
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
