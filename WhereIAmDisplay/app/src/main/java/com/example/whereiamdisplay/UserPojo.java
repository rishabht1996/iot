package com.example.whereiamdisplay;

/**
 * Created by Rishabh on 19/07/2017.
 */

public class UserPojo  {


    private String name,email,mobile,pass,userId;

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
        return "UserPojo{" +
                "userId='" + userId + '\'' +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
