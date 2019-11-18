package com.example.familymap.Requests;



/**
 * This requests a login for the user
 */

public class Login_Request {
    private String userName;
    private String password;

    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    public void setUserName(String username){
        this.userName = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
