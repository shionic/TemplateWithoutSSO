package com.github.shionic.backendexample.exceptions;

public class MyCustomException extends RuntimeException {
    public MyCustomException() {
    }

    public MyCustomException(String message) {
        super(message);
    }

    public MyCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
