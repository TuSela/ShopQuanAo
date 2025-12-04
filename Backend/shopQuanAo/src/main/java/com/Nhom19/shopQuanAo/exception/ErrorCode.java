package com.Nhom19.shopQuanAo.exception;


public enum ErrorCode {

    USER_ID_NOT_EXISTED(1004,"USER_ID_NOT_EXISTED"),
    USER_NOT_EXISTED(1006,"USER_NOT_EXISTED"),
    UNUATHENTICATION(1006,"UNUATHENTICATION"),
    USERNAME_INVALID(1003,"username is invalid"),
    PASSWORD_INVALID(1005,"password is invalid"),
    PASSWORD_CONFIRM_NOT_MATCH(1008,"password confirm is not match"),
    UNAUTHORIZED(9999, "Unauthorized"),
    USER_EXISTED(1002,"user already exists"),
    INVALID_REQUEST(1007,"invalid request"),
    ;


    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
