package com.example.familymap.Models;

/**
 * Authorization Model
 */
public class AuthorizationToken_Model {
    private String AuthorizationToken;
    private String UserName;
    public String getAuthorizationToken(){
        return AuthorizationToken;
    }
    public void setAuthorizationToken(String AuthToken){
        this.AuthorizationToken = AuthToken;
    }
    public String getUserName(){
        return UserName;
    }
    public void setUserName(String userName){
        this.UserName = userName;
    }
    public AuthorizationToken_Model(String authToken, String userName){
        this.AuthorizationToken = authToken;
        this.UserName = userName;
    }
    public AuthorizationToken_Model(){}
}
