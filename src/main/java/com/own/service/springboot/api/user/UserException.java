package com.own.service.springboot.api.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Spring on 2017/7/4.
 */
public class UserException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(UserException.class);

    public UserException() {
        this("");
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(Exception e) {
        super(e);
    }

    public static class TokenInvalidException extends UserException {
        public TokenInvalidException(String message) {
            super(message);
        }
    }
}
