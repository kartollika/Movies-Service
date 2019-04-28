package com.pau_pau.project.models.states.concretes;

import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.states.FilmState;
import com.pau_pau.project.models.states.FilmStatus;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("approved")
public class ApprovedFilmState extends FilmState {

    private String comment;

    public ApprovedFilmState() {
    }

    public ApprovedFilmState(FilmState oldState) {
        super(oldState);
    }

    @Override
    public void publish(Account account) {
    }

    @Override
    public void reject(Account account, String comment) {
        if (account.getPermissionsLevel().equals(Role.ADMIN)) {
            film.setState(new RejectedFilmState(film.getState(), comment));
        }
    }

    @Override
    public FilmStatus getStatusName() {
        return FilmStatus.APPROVED;
    }
}
