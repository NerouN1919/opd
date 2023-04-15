package com.project.opd.Errors;

public class AuthException extends RuntimeException{

    public AuthException(String message) {
        super(message);
    }
}