package com.pau_pau.project.models.states;

import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;

import javax.persistence.Column;

public class RejectedState extends State {

    @Column
    protected String comment;

    public RejectedState(State oldState) {
        super(oldState);
    }

    @Override
    public void publish(Account account) {
        if (account.getPermissionsLevel().equals(Role.EDITOR)) {
            film.setState(new ModifiedState(film.getState()));
        }

        if (account.getPermissionsLevel().equals(Role.ADMIN)) {
            film.setState(new ApprovedState(film.getState()));
        }
    }

    @Override
    public void reject(Account account) {
    }
}
