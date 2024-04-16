package com.romm.todopp.exceptions;

public class AlreadyLinkedException extends RuntimeException {
    public AlreadyLinkedException(String msg, Throwable err) {
        super(msg, err);
    }
}
