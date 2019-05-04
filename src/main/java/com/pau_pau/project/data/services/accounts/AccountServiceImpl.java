package com.pau_pau.project.data.services.accounts;

import com.pau_pau.project.common.utils.PasswordEncoderUtil;
import com.pau_pau.project.data.repository.accounts.AccountsRepository;
import com.pau_pau.project.data.repository.films.FilmsRepository;
import com.pau_pau.project.data.services.films.FilmsService;
import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.films.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pau_pau.project.data.controllers.ControllerConstants.MAX_HISTORY_SIZE;

@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private FilmsRepository filmsRepository;

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
        Film filmById = filmsService.findFilmById(filmId);
        holder.getWishlist().add(filmById);
        accountsRepository.save(holder);
        return filmById;
    }

    @Override
    public Film deleteFromWishlist(String username, int filmId) throws Exception {
        Account holder = accountsRepository.findByUsername(username).orElseThrow(Exception::new);
        Map<Boolean, List<Film>> partitionById = holder.getWishlist().stream()
                .collect(Collectors.partitioningBy((Film f) -> f.getId() == filmId));
        holder.setWishlist(partitionById.get(false));
        accountsRepository.save(holder);

        List<Film> filmToDelete = partitionById.get(true);
        if (filmToDelete.isEmpty()) {
            throw new Exception();
        } else {
            return filmToDelete.get(0);
        }
    }

    @Override
    public Film addToHistory(String username, int filmId) throws Exception{
        //System.out.println("AccountService addToHistory");
        Account account = accountsRepository.findByUsername(username).orElseThrow(Exception::new);
        Film film = filmsRepository.findById(filmId).orElseThrow(Exception::new);
        List<Film> filmList = account.getHistory();
        filmList.add(film);
        int size = filmList.size();
        if (size == MAX_HISTORY_SIZE + 1){
            filmList.remove(0);
        }
        account.setHistory(filmList);
        accountsRepository.save(account);
        return film;
    }
    @Override
    public Account getAccount() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findByUsername(authentication.getName());
    }

    @Override
    public Account findById(int id) throws Exception {
        return accountsRepository.findById(id).orElseThrow(Exception::new);
    }

}
