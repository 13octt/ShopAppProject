package com.sales.shopapp.exception;

import com.sales.shopapp.enums.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ApplicationException extends RuntimeException {
    private ErrorCode errorCode;
}