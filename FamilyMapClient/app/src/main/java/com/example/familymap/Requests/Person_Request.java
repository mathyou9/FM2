package com.example.familymap.Requests;

/**
 * request person
 */
public class Person_Request {
    private String authToken;
    private String personID;
    public String getAuthorizationTokens(){
        return authToken;
    }
    public String getPersonID(){
        return personID;
    }
    public void setAuthorizationTokens(String authorizationTokens){
        this.authToken = authorizationTokens;
    }
    public void setPersonID(String id){
        this.personID = id;
    }
}
