package com.pau_pau.project.data.services.accounts;

import com.pau_pau.project.common.utils.PasswordEncoderUtil;
import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.data.repository.histories.HistoryRepository;
import com.pau_pau.project.data.repository.accounts.AccountsRepository;
import com.pau_pau.project.data.services.films.FilmsService;
import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.films.Film;
import com.pau_pau.project.models.history.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private HistoryRepository historyRepository;

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
    public Film addFilmToHistory(Film film){
        try {
            String username = getAccount().getUsername();
            Account account = accountsRepository.findByUsername(username).orElseThrow(InstanceNotFoundException::new);
            Set<History> historySet = account.getHistorySet();
            int setSize = historySet.size();
            History foundHistory = null;
            for(History history : historySet){
                if (history.getFilm().equals(film)){
                    foundHistory = history;
                    break;
                }
            }
            if (foundHistory == null){
                if (setSize < ControllerConstants.MAX_HISTORY_SIZE) {
                    History historyToAdd = new History(account, film, setSize + 1);
                    historySet.add(historyToAdd);
                    historyRepository.save(historyToAdd);
                }else{
                    History historyMinOrder = History.getHistoryWithMinOrder(historySet);
                    historySet.remove(historyMinOrder);
                    historyRepository.delete(historyMinOrder);
                    for (History history : historySet){
                        history.setFilmOrder(history.getFilmOrder() - 1);
                        historyRepository.save(history);
                    }
                    History historyToAdd = new History(account, film, ControllerConstants.MAX_HISTORY_SIZE) ;
                    historySet.add(historyToAdd);
                    historyRepository.save(historyToAdd);
                }
            }else{
                int order = foundHistory.getFilmOrder();
                historySet.remove(foundHistory);
                historyRepository.delete(foundHistory);
                for(History history : historySet){
                    if (history.getFilmOrder() > order){
                        historyRepository.delete(history);
                        history.setFilmOrder(history.getFilmOrder() - 1);
                        historyRepository.save(history);
                    }
                }
                History historyToAdd = new History(account, film, historySet.size() + 1) ;
                historySet.add(historyToAdd);
                historyRepository.save(historyToAdd);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return film;
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
    public Account getAccount() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usermame = authentication.getName();
        return findByUsername(usermame);
    }

    @Override
    public Account findById(int id) throws Exception {
        return accountsRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public List<Film> getAllActiveRequests() {
        return filmsService.findActiveRequests();
    }

    @Override
    public List<Film> getActiveRequestsForAccount(int id) {
        return filmsService.findActiveRequestsForAccount(id);
    }
}
