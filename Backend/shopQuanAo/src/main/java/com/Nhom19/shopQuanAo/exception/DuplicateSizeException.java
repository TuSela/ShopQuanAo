package com.Nhom19.shopQuanAo.exception;

public class DuplicateSizeException extends RuntimeException {
    public DuplicateSizeException() {
        super("Size already exists");
    }
}
