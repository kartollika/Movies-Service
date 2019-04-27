package com.pau_pau.project.models.states.concretes;

import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.states.Commentable;
import com.pau_pau.project.models.states.FilmState;

import javax.persistence.Column;

public class ModifiedFilmState extends FilmState implements Commentable {

    @Column
    protected String comment;

    public ModifiedFilmState(FilmState oldState) {
        super(oldState);
    }

    @Override
    public void publish(Account account) {
        if (account.getPermissionsLevel().equals(Role.ADMIN)) {
            film.setState(new ApprovedFilmState(film.getState()));
        }
    }

    @Override
    public void reject(Account account) {
        if (account.getPermissionsLevel().equals(Role.ADMIN)) {
            film.setState(new RejectedFilmState(film.getState()));
        }
    }
}
