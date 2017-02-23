package com.example.smsapp;

/**
 * Created by Mohit.Aggarwal on 2/23/2017.
 */

public class SMSData {

    // Number from witch the sms was send
    private String number;
    // SMS text body
    private String body;
    private String date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String type;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}