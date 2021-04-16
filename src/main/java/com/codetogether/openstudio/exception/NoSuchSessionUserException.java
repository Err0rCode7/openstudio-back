package com.codetogether.openstudio.exception;

public class NoSuchSessionUserException extends RuntimeException{
    public NoSuchSessionUserException() {
    }

    public NoSuchSessionUserException(String message) {
        super(message);
    }
}
