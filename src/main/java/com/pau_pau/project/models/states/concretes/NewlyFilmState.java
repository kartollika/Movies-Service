package com.pau_pau.project.models.states.concretes;

import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.states.FilmState;

import javax.naming.NoPermissionException;

public class NewlyFilmState extends FilmState {

    public NewlyFilmState(Account account) {
        super(account);
    }

    @Override
    public void publish(Account account) throws NoPermissionException {
        if (account.getPermissionsLevel().equals(Role.ADMIN)) {
            film.setState(new ApprovedFilmState(account));
        }
    }

    @Override
    public void reject(Account account) {
    }
}
