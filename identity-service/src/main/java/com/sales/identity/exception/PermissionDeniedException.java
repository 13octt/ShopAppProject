package com.sales.identity.exception;

public class PermissionDeniedException extends Exception{
    public PermissionDeniedException(String message)
    {
        super(message);
    }
}