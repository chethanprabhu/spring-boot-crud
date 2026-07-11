package com.example.demo.exception;

public class PersonAgeLimitException extends RuntimeException {
    public PersonAgeLimitException(String message) {
        super(message);
    }
}
