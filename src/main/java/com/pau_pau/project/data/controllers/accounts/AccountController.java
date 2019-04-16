package com.pau_pau.project.data.controllers.accounts;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.models.accounts.AccountDto;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.films.FilmDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface AccountController {

    @ApiOperation(value = "Get account information by authentication token", response = AccountDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    AccountDto getAccountInfoByAuthentication();

    @ApiOperation(value = "[ADMIN] Get account information by username ", response = AccountDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(value = ControllerConstants.ACCOUNT_USERNAME)
    @ResponseStatus(HttpStatus.OK)
    AccountDto getAccountInfoByUsername(@PathVariable String username);

    @ApiOperation(value = "Get user's wish list by authentication", response = FilmDTO.class, responseContainer = "List")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(value = ControllerConstants.WISHLIST_WITH_AUTHENTICATION)
    @ResponseStatus(HttpStatus.OK)
    List<FilmDTO> getWishlistByAuthentication();

    @ApiOperation(value = "Get user's history by authentication", response = FilmDTO.class, responseContainer = "List")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(value = ControllerConstants.HISTORY_WITH_AUTHENTICATION)
    @ResponseStatus(HttpStatus.OK)
    List<FilmDTO> getHistoryByAuthentication();

    @ApiOperation(value = "Account registration")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AccountDto registration(@RequestBody AccountDto accountDto);

    @ApiOperation(value = "Update account role")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @PutMapping(value = ControllerConstants.CHANGE_ROLE)
    @ResponseStatus(HttpStatus.OK)
    AccountDto updateAccountRole(@PathVariable String username,
                                 @RequestParam Role newRole);

    @ApiOperation(value = "Add film in wish list by authentication")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @PutMapping(value = ControllerConstants.WISHLIST_WITH_AUTHENTICATION)
    @ResponseStatus(HttpStatus.CREATED)
    FilmDTO addToWishlistByAuthentication(@RequestParam int filmId);


    @ApiOperation(value = "Delete film from wish list by authentication")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @DeleteMapping(value = ControllerConstants.WISHLIST_WITH_AUTHENTICATION)
    @ResponseStatus(HttpStatus.OK)
    FilmDTO deteleFromWishlistByAuthentication(@RequestParam int filmId);

}
