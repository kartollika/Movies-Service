package com.pau_pau.project.services;

import com.pau_pau.project.data.models.Film;
import com.pau_pau.project.models.Account;
import com.pau_pau.project.models.Role;

import java.util.List;

public interface AccountService {

    Account save(Account account);

    Account updateRole(String username, Role role) throws Exception;

    Account findByUsername(String username) throws Exception;

    Film addToWishlist(String username, int filmId) throws Exception;

    List<Film> deleteFromWishlist(String username, int filmId) throws Exception;
}