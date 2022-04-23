package com.ashraf.ojapilayer.exception;

public class NonRetryableException extends RuntimeException {
    public NonRetryableException(String msg) {
        super(msg);
    }
}
