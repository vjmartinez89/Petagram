package com.vjmartinez.petagram.exception;

public class NetworkException extends Exception {

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NetworkException(String code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NetworkException{");
        sb.append("code='").append(code).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
