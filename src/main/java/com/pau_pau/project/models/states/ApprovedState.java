package com.pau_pau.project.models.states;

import com.pau_pau.project.models.accounts.Account;

public class ApprovedState extends State {

    public ApprovedState(Account account) {
        super(account);
    }

    public ApprovedState(State oldState) {
        super(oldState);
    }

    @Override
    public void publish(Account account) {
    }

    @Override
    public void reject(Account account) {
    }
}
