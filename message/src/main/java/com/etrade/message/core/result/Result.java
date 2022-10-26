package com.etrade.message.core.result;

public abstract class Result {
    private String message;
    private boolean success;

    public Result( boolean success) {
        this.success = success;
    }

    public Result(boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    public String getMessage(){
        return this.message;
    }

    public boolean isSuccess(){
        return this.success;
    }
}
