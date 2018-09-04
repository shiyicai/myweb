package com.springboot.response;

public class ApiResponseBody extends BaseResponseBody {

    private Object body;

    public ApiResponseBody(String code, String message) {
        super(code, message);
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
