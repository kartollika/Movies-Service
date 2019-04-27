package com.pau_pau.project.models.states.concretes;

import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.states.FilmState;

public class ApprovedFilmState extends FilmState {

    public ApprovedFilmState(Account account) {
        super(account);
    }

    public ApprovedFilmState(FilmState oldState) {
        super(oldState);
    }

    @Override
    public void publish(Account account) {
    }

    @Override
    public void reject(Account account) {
        if (account.getPermissionsLevel().equals(Role.ADMIN)) {
            film.setState(new RejectedFilmState(film.getState()));
        }
    }
}
