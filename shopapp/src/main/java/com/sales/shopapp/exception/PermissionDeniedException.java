package com.sales.shopapp.exception;

public class PermissionDeniedException extends Exception{
    public PermissionDeniedException(String message)
    {
        super(message);
    }
}
