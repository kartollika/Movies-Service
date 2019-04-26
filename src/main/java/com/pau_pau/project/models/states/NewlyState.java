package com.pau_pau.project.models.states;

import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;

import javax.naming.NoPermissionException;

public class NewlyState extends State {

    public NewlyState(Account account) {
        super(account);
    }

    @Override
    public void publish(Account account) throws NoPermissionException {
        if (account.getPermissionsLevel().equals(Role.ADMIN)) {
            film.setState(new ApprovedState(account));
        }
    }

    @Override
    public void reject(Account account) {

    }
}
