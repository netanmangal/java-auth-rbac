package com.nm.authrbac.entity;

public class Response {

    public enum SUCCESS_STATUS {
        TRUE,
        FALSE
    }

    private SUCCESS_STATUS success;
    private Object message;

    public Response(SUCCESS_STATUS success) {
        this.success = success;
    }

    public Response(SUCCESS_STATUS success, Object msg) {
        this.success = success;
        this.message = msg;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public SUCCESS_STATUS getSuccess() {
        return success;
    }

    public void setSuccess(SUCCESS_STATUS success) {
        this.success = success;
    }

}
