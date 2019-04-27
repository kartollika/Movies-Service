package com.pau_pau.project.models.states;

import javax.persistence.Column;

public interface Commentable {

    @Column
    String comment = null;
}
