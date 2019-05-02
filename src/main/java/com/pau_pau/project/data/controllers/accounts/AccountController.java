package com.pau_pau.project.data.controllers.accounts;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.models.accounts.AccountDto;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.films.FilmDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pau_pau.project.data.controllers.ControllerConstants.*;

public interface AccountController {

    @ApiOperation(value = "Get account information by authentication token. " + AVAILABLE_EVERYONE, response = AccountDto.class, authorizations = @Authorization(value = "Bearer"))
    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER", "ROLE_EDITOR"})
    @ResponseStatus(HttpStatus.OK)
    AccountDto getAccountInfoByAuthentication();

    @ApiOperation(value = "[ADMIN] Get account information by username. " + AVAILABLE_ADMIN, response = AccountDto.class, authorizations = @Authorization(value = "Bearer"))
    @GetMapping(value = ControllerConstants.ACCOUNT_USERNAME)
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    AccountDto getAccountInfoByUsername(@PathVariable String username);

    @ApiOperation(value = "Account registration. " + AVAILABLE_EVERYONE)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AccountDto registration(@RequestBody AccountDto accountDto);

    @ApiOperation(value = "Update account role. " + AVAILABLE_ADMIN, response = AccountDto.class, authorizations = @Authorization(value = "Bearer"))
    @PutMapping(value = ControllerConstants.CHANGE_ROLE)
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    AccountDto updateAccountRole(@PathVariable String username,
                                 @RequestParam Role newRole);

    @ApiOperation(value = "Get user's wish list by authentication. " + AVAILABLE_EVERYONE, response = FilmDTO.class, responseContainer = "List")
    @GetMapping(value = ControllerConstants.WISHLIST_WITH_AUTHENTICATION)
    @ResponseStatus(HttpStatus.OK)
    List<FilmDTO> getWishlistByAuthentication();

    @ApiOperation(value = "Add film in wish list by authentication. " + AVAILABLE_EVERYONE, response = FilmDTO.class, authorizations = @Authorization(value = "Bearer"))
    @PutMapping(value = ControllerConstants.WISHLIST_WITH_AUTHENTICATION)
    @ResponseStatus(HttpStatus.CREATED)
    FilmDTO addToWishlistByAuthentication(@RequestParam int filmId);


    @ApiOperation(value = "Delete film from wish list by authentication. " + AVAILABLE_EVERYONE, response = FilmDTO.class)
    @DeleteMapping(value = ControllerConstants.WISHLIST_WITH_AUTHENTICATION)
    @ResponseStatus(HttpStatus.OK)
    FilmDTO deteleFromWishlistByAuthentication(@RequestParam int filmId);

    @ApiOperation(value = "Get account information by id. " + AVAILABLE_EDITOR_ADMIN, response = AccountDto.class, authorizations = @Authorization(value = "Bearer"))
    @GetMapping(value = ControllerConstants.ACCOUNT_ID)
    @Secured({"ROLE_EDITOR", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    AccountDto getAccountInfoById(@PathVariable int id);

}
