package com.example.demo.exception;

public class SortFieldNotAllowedException extends RuntimeException{
    public SortFieldNotAllowedException(String message) {
        super(message);
    }
}
