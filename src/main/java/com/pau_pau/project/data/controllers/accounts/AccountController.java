package com.pau_pau.project.data.controllers.accounts;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.models.accounts.AccountDto;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.films.FilmDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pau_pau.project.data.controllers.ControllerConstants.AVAILABLE_ADMIN;
import static com.pau_pau.project.data.controllers.ControllerConstants.AVAILABLE_EVERYONE;

public interface AccountController {

    @ApiOperation(value = "Get account information by authentication token. " + AVAILABLE_EVERYONE, response = AccountDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    AccountDto getAccountInfoByAuthentication();

    @ApiOperation(value = "[ADMIN] Get account information by username. " + AVAILABLE_ADMIN, response = AccountDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(value = ControllerConstants.ACCOUNT_USERNAME)
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    AccountDto getAccountInfoByUsername(@PathVariable String username);

    @ApiOperation(value = "Account registration. " + AVAILABLE_EVERYONE)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AccountDto registration(@RequestBody AccountDto accountDto);

    @ApiOperation(value = "Update account role. " + AVAILABLE_ADMIN, response = AccountDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @PutMapping(value = ControllerConstants.CHANGE_ROLE)
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    AccountDto updateAccountRole(@PathVariable String username,
                                 @RequestParam Role newRole);

    @ApiOperation(value = "Get user's wish list by authentication. " + AVAILABLE_EVERYONE, response = FilmDTO.class, responseContainer = "List")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(value = ControllerConstants.WISHLIST_WITH_AUTHENTICATION)
    @ResponseStatus(HttpStatus.OK)
    List<FilmDTO> getWishlistByAuthentication();

    @ApiOperation(value = "Add film in wish list by authentication. " + AVAILABLE_EVERYONE, response = FilmDTO.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @PutMapping(value = ControllerConstants.WISHLIST_WITH_AUTHENTICATION)
    @ResponseStatus(HttpStatus.CREATED)
    FilmDTO addToWishlistByAuthentication(@RequestParam int filmId);


    @ApiOperation(value = "Delete film from wish list by authentication. " + AVAILABLE_EVERYONE, response = FilmDTO.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @DeleteMapping(value = ControllerConstants.WISHLIST_WITH_AUTHENTICATION)
    @ResponseStatus(HttpStatus.OK)
    FilmDTO deteleFromWishlistByAuthentication(@RequestParam int filmId);

}
