package com.pau_pau.project.models.states;

public class DeadEndStateException extends RuntimeException {

    public DeadEndStateException() {
    }

    public DeadEndStateException(String message) {
        super(message);
    }
}
