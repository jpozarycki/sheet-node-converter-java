package com.jpozarycki.sheetNodeConverter.util;

public class InvalidWorkbookException extends RuntimeException {
    public InvalidWorkbookException(Throwable cause) {
        super("Error on retrieving workbook", cause);
    }

    public InvalidWorkbookException(String message) {
        super(message);
    }
}
