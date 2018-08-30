package com.springboot.response;


public class InsertResponseBody {
    private int code;
    private String primaryKey;
    private String message;

    public InsertResponseBody(int code,String primaryKey){
        setCode(code);
        setPrimaryKey(primaryKey);
    }


    public void setCode(int code) {
        this.code = code;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public String getMessage() {
        return message;
    }

    public InsertResponseBody build(){
        try {
            switch (this.code){
                case 1:
                    setMessage("create success");
                    break;
                case 0:
                    setMessage("create failure");
                    break;
                default:
                    throw new IllegalArgumentException("Illegal code " + this.code);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return this;
    }



}
