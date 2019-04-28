package com.pau_pau.project.models.states.concretes;

import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.states.FilmState;
import com.pau_pau.project.models.states.FilmStatus;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("new")
public class NewlyFilmState extends FilmState {

    public NewlyFilmState() {
    }

    public NewlyFilmState(Account account) {
        super(account);
    }

    @Override
    public void publish(Account account) {
        Role permissionLevel = account.getPermissionsLevel();

        if (permissionLevel.equals(Role.ADMIN)) {
            film.setState(new ApprovedFilmState(film.getState()));
        }

        if (permissionLevel.equals(Role.EDITOR)) {
            film.setState(new ModifiedFilmState(film.getState()));
        }
    }

    @Override
    public void reject(Account account, String comment) {
    }

    @Override
    public FilmStatus getStatusName() {
        return FilmStatus.NEWLY;
    }
}
