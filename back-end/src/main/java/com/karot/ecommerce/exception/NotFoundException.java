package com.karot.ecommerce.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class NotFoundException extends RuntimeException {
    public NotFoundException (String message){
        super(message);
    }
}
