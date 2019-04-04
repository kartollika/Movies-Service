package com.pau_pau.project.services;

import com.pau_pau.project.data.models.Film;
import com.pau_pau.project.data.services.FilmsService;
import com.pau_pau.project.database.AccountsRepository;
import com.pau_pau.project.models.Account;
import com.pau_pau.project.models.Role;
import com.pau_pau.project.utils.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public void save(Account account) {
        account.setPassword(passwordEncoderUtil.passwordEncoder().encode(account.getPassword()));
        accountsRepository.save(account);
    }

    @Override
    public void updateRole(String username, Role role) throws Exception {
        Account updatingAccount = accountsRepository.findByUsername(username).orElseThrow(Exception::new);
        updatingAccount.setPermissionsLevel(role);
        accountsRepository.save(updatingAccount);
    }

    @Override
    public Account findByUsername(String username) throws Exception {
        return accountsRepository.findByUsername(username).orElseThrow(Exception::new);
    }

    @Override
    public void addInWishlist(String username, int filmId) throws Exception {
        Account holder = accountsRepository.findByUsername(username).orElseThrow(Exception::new);
        Film filmById = Film.fromFilmDTOModel(filmsService.findFilmById(filmId));
        holder.getWishlist().add(filmById);
        accountsRepository.save(holder);
    }

    @Override
    public void deleteFromWishlist(String username, int filmId) throws Exception {
        Account holder = accountsRepository.findByUsername(username).orElseThrow(Exception::new);
        holder.setWishlist(holder.getWishlist().stream()
                .filter((Film f) -> f.getId() != filmId)
                .collect(Collectors.toList()));
        accountsRepository.save(holder);
    }


}
