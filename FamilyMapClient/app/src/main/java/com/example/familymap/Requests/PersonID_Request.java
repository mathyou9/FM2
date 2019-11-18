package com.example.familymap.Requests;

/**
 * personid request
 */
public class PersonID_Request {
    private String userName;
    private String password;
    private String personID;
    private String authorizationToken;
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    public void setPersonID(String personID2){
        personID = personID2;
    }
    public void setAuthorizationToken(String auth){
        authorizationToken = auth;
    }
}
