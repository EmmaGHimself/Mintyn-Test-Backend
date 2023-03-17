package com.mintyn.test.exception;

import com.mintyn.test.common.exception.BaseRuntimeException;
import org.springframework.http.HttpStatus;

public class NotEnoughQuantityException extends BaseRuntimeException {

    public NotEnoughQuantityException(String message) {
        super("Not enough quantity for " + message, HttpStatus.BAD_REQUEST);
    }
}