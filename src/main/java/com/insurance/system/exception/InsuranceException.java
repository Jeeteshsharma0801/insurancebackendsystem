package com.insurance.system.exception;

import lombok.Data;

@Data
public class InsuranceException extends RuntimeException{

    private String message;

    private Integer code;
    public InsuranceException(Integer code ,String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
