package com.pau_pau.project.models.states.concretes;

import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.states.Commentable;
import com.pau_pau.project.models.states.FilmState;

public class RejectedFilmState extends FilmState implements Commentable {

    public RejectedFilmState(FilmState oldState) {
        super(oldState);
    }

    @Override
    public void publish(Account account) {
        if (account.getPermissionsLevel().equals(Role.EDITOR)) {
            film.setState(new ModifiedFilmState(film.getState()));
        }

        if (account.getPermissionsLevel().equals(Role.ADMIN)) {
            film.setState(new ApprovedFilmState(film.getState()));
        }
    }

    @Override
    public void reject(Account account) {
    }
}
