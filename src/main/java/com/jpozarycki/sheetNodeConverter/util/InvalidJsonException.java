package com.jpozarycki.sheetNodeConverter.util;

public class InvalidJsonException extends RuntimeException {

    public InvalidJsonException(Throwable cause) {
        super("Error on handling json", cause);
    }
}
