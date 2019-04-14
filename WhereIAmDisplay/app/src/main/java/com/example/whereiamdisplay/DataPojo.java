package com.example.whereiamdisplay;

public class DataPojo {
    private String name;
    private String email;
    private String mobile;
    private String pass;
    private String userId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNoti() {
        return noti;
    }

    public void setNoti(String noti) {
        this.noti = noti;
    }

    private String message;
    private String noti;



    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPass() {
        return pass;
    }
    public String getUserId(){
        return  userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DataPojo{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", pass='" + pass + '\'' +
                ", userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                ", noti='" + noti + '\'' +
                '}';
    }
}
