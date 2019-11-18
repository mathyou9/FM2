package com.example.familymap.Requests;

/**
 * this will get info about a users family
 */
public class Fill_Request {
    private String userName;
    private int generations;
    public String getUserName(){
        return userName;
    }
    public void setUserName(String username){userName = username;}
    private int getGenerations(){ return generations; }
    private void setGenerations(int numGenerations){generations = numGenerations;}

}
