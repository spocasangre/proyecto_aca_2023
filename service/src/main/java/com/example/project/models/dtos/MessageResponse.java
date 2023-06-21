package com.example.project.models.dtos;

public class MessageResponse {

    private Boolean success;
    private int errorCode;
    private Object data;
    private String message;

    public MessageResponse(Boolean success, int errorCode, Object data, String message) {
        this.success = success;
        this.errorCode = errorCode;
        this.data = data;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    public int getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
