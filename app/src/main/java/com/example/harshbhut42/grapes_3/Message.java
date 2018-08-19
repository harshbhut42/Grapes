package com.example.harshbhut42.grapes_3;

public class Message {

    public String  message;
    public String send_by;
    public long time;

    public Message(){}
    public Message(String message, String send_by, long time) {
        this.message = message;
        this.send_by = send_by;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSend_by() {
        return send_by;
    }

    public void setSend_by(String send_by) {
        this.send_by = send_by;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
