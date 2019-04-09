package com.pau_pau.project.data.controllers.accounts;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.data.services.accounts.AccountService;
import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.AccountDto;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.films.Film;
import com.pau_pau.project.models.films.FilmDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    public AccountDto getAccountInfoByUsername(String username) {
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

    public AccountDto registration(AccountDto accountDto) {
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

    public AccountDto updateAccountRole(String username,
                                        Role newRole) {
        try {
            Account account = accountService.updateRole(username, newRole);
            return AccountDto.dtoFromAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    public FilmDTO addToWishlistByAuthentication(int filmId) {
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

    public FilmDTO deteleFromWishlistByAuthentication(int filmId) {
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
