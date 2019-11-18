package com.example.familymap.Models;

/**
 * Person Model
 */
public class Person_Model {
    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;
    public Person_Model(String aU, String fN, String lN){
        personID = "";
        associatedUsername = aU;
        firstName = fN;
        lastName = lN;
        gender = "";
        fatherID = "";
        motherID = "";
        spouseID = "";
    }
    public Person_Model(String pID, String aU, String fN, String lN, String g, String fID, String mID, String sID){
        personID = pID;
        associatedUsername = aU;
        firstName = fN;
        lastName = lN;
        gender = g;
        fatherID = fID;
        motherID = mID;
        spouseID = sID;
    }
    public Person_Model(User_Model user){
        personID = user.getPersonID();
        associatedUsername = user.getUserName();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        gender = user.getGender();

    }

    public String getPersonID() {
        return personID;
    }
    public String getAssociatedUsername(){
        return associatedUsername;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getGender(){
        return gender;
    }
    public String getFatherID(){
        return fatherID;
    }
    public String getMotherID(){
        return motherID;
    }
    public String getSpouseID(){
        return spouseID;
    }
    public void setPersonID(){}
    public void setAssociatedUsername(){}
    public void setFirstName(){}
    public void setLastName(){}
    public void setGender(){}
    public void setFatherID(String fID){
        this.fatherID = fID;
    }
    public void setMotherID(String mID){
        this.motherID = mID;
    }
    public void setSpouseID(){}
}
