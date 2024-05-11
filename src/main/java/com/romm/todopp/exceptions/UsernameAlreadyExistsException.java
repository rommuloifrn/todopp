package com.romm.todopp.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String msg, Throwable err) {
        super(msg, err);
    }
}
