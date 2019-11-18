package com.example.familymap.Results;

/**
 * holds data for registering user
 */
public class Register_Results {
    private String authToken;
    private String personID;
    private String userName;
    private String message;

    public Register_Results(String authToken, String userName, String personID){
        this.authToken = authToken;
        this.personID = personID;
        this.userName = userName;
    }

    public Register_Results(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public String getPersonID(){
        return personID;
    }
    public void setPersonID(String personID){
        this.personID = personID;
    }

    public String getAuthorizationToken(){
        return authToken;
    }
    public void setAuthorizationToken(String authToken){
        this.authToken = authToken;
    }

    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
}
