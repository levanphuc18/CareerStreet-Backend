package com.careerstreet.admin_service.exception;

public class EntityExitsException extends GlobalException {
    public EntityExitsException(String message, String code) {
        super(code, message);
    }
}
