package com.pau_pau.project.data.services.accounts;

import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.films.Film;

import java.util.List;

public interface AccountService {

    Account save(Account account);

    Account updateRole(String username, Role role) throws Exception;

    Account findByUsername(String username) throws Exception;

    Film addToWishlist(String username, int filmId) throws Exception;

    Film deleteFromWishlist(String username, int filmId) throws Exception;

    Account getAccount() throws Exception;

    Film addToHistory(String username, int filmId) throws Exception;

    Account findById(int id) throws Exception;

    List<Film> getAllActiveRequests();

    List<Film> getActiveRequestsForAccount(int id);
}