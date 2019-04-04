package com.pau_pau.project.data.controllers.accounts;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.data.models.FilmDTO;
import com.pau_pau.project.models.Account;
import com.pau_pau.project.models.AccountDto;
import com.pau_pau.project.models.Role;
import com.pau_pau.project.services.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@Api(tags = "Accounts", value = "Accounts", description = "Api for operations with accounts")
@RequestMapping(ControllerConstants.ACCOUNT_URL)

public class AccountController {

    @Autowired
    private AccountService accountService;

    /* ================================
             GET METHODS
    ================================== */

    @ApiOperation(value = "Get account information by authentication token", response = AccountDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping
    public AccountDto getAccountInfoByAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return getAccountInfoByUsername(name);
    }


    @ApiOperation(value = "[ADMIN] Get account information by username ", response = AccountDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(value = ControllerConstants.ACCOUNT_USERNAME)
    public AccountDto getAccountInfoByUsername(@PathVariable String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            return AccountDto.dtoFromAccount(accountService.findByUsername(username));

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

    }

    @ApiOperation(value = "Get user's wish list by authentication", response = FilmDTO.class, responseContainer = "List")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(value = ControllerConstants.WISHLIST_WITH_AUTHENTICATION)
    @ResponseStatus(HttpStatus.OK)
    public List<FilmDTO> getWishlistByAuthentication() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return accountService
                .findByUsername(username)
                .getWishlist()
                .stream()
                .map(FilmDTO::fromFilmModel)
                .collect(Collectors.toList());
    }


    /* ================================
                 POST METHODS
     ================================== */

    @ApiOperation(value = "Account registration")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@RequestBody AccountDto accountDto) {
        accountDto.setPermissionsLevel(Role.USER);
        try {
            accountService.save(prepareAccountModelFromDtoWithRole(accountDto));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }


    /* ================================
                 PUT METHODS
     ================================== */

    @ApiOperation(value = "Update account role")
    @PutMapping(value = ControllerConstants.CHANGE_ROLE)
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

    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @ApiOperation(value = "Add film in wish list by authentication")
    @PutMapping(value = ControllerConstants.WISHLIST_WITH_AUTHENTICATION)
    @ResponseStatus(HttpStatus.CREATED)
    public void addToWishlistByAuthentication(@RequestParam int filmId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            accountService.addInWishlist(username, filmId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

    }


    /* ================================
                 DELETE METHODS
     ================================== */


    @ApiOperation(value = "Delete film from wish list by authentication")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @DeleteMapping(value = ControllerConstants.WISHLIST_WITH_AUTHENTICATION)
    @ResponseStatus(HttpStatus.OK)
    public void deteleFromWishlistByAuthentication(@RequestParam int filmId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            accountService.deleteFromWishlist(username, filmId);
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
