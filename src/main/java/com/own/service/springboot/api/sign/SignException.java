package com.own.service.springboot.api.sign;

/**
 * Created by Spring on 2017/7/4.
 */
public class SignException extends Exception {
    public SignException(String message) {
        super(message);
    }

    public SignException(Exception e) {
        super(e);
    }

    public static class SignParamIllegalException extends SignException {

        public SignParamIllegalException(String message) {
            super(message);
        }
    }

    public static class SignVersionIllegalException extends SignException {

        public SignVersionIllegalException(String message) {
            super(message);
        }
    }

    public static class SignTimeoutException extends SignException {

        public SignTimeoutException(String message) {
            super(message);
        }
    }
}
