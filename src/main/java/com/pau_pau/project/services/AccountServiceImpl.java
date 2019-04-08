package com.pau_pau.project.services;

import com.pau_pau.project.data.models.Film;
import com.pau_pau.project.data.services.FilmsService;
import com.pau_pau.project.database.AccountsRepository;
import com.pau_pau.project.models.Account;
import com.pau_pau.project.models.Role;
import com.pau_pau.project.utils.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private FilmsService filmsService;

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    @Override
    public Account save(Account account) {
        account.setPassword(passwordEncoderUtil.passwordEncoder().encode(account.getPassword()));
        accountsRepository.save(account);
        return account;
    }

    @Override
    public Account updateRole(String username, Role role) throws Exception {
        Account updatingAccount = accountsRepository.findByUsername(username).orElseThrow(Exception::new);
        updatingAccount.setPermissionsLevel(role);
        accountsRepository.save(updatingAccount);
        return updatingAccount;
    }

    @Override
    public Account findByUsername(String username) throws Exception {
        return accountsRepository.findByUsername(username).orElseThrow(Exception::new);
    }

    @Override
    public Film addToWishlist(String username, int filmId) throws Exception {
        Account holder = accountsRepository.findByUsername(username).orElseThrow(Exception::new);
        Film filmById = Film.fromFilmDTOModel(filmsService.findFilmById(filmId));
        holder.getWishlist().add(filmById);
        accountsRepository.save(holder);
        return filmById;
    }

    @Override
    public List<Film> deleteFromWishlist(String username, int filmId) throws Exception {
        Account holder = accountsRepository.findByUsername(username).orElseThrow(Exception::new);
        Map<Boolean, List<Film>> partitionById = holder.getWishlist().stream()
                .collect(Collectors.partitioningBy((Film f) -> f.getId() == filmId));
        holder.setWishlist(partitionById.get(false));
        accountsRepository.save(holder);
        return partitionById.get(true);
    }


}
