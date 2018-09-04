package com.springboot.response;

public class BaseResponseBody {
    private final String code;
    private final String message;

    public BaseResponseBody(String code,String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
