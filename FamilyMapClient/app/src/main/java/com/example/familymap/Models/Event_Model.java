package com.example.familymap.Models;

/**
 * Event Model
 */
public class Event_Model {
    private String eventID;
    private String associatedUsername;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    public Event_Model(){
        eventID = "";
        associatedUsername = "";
        personID = "";
        latitude = 0;
        longitude = 0;
        country = "";
        city = "";
        eventType = "";
        year = 0;
    }
    public Event_Model(String id, String uN, String pID, double la, double lo, String Co, String Ci, String ET, int Ye){
        eventID = id;
        associatedUsername = uN;
        personID = pID;
        latitude = la;
        longitude = lo;
        country = Co;
        city = Ci;
        eventType = ET;
        year = Ye;
    }

    public String getEventID(){
        return eventID;
    }
    public String getAssociatedUsername(){
        return associatedUsername;
    }
    public String getPersonID(){
        return personID;
    }
    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }
    public String getCountry(){
        return country;
    }
    public String getCity(){
        return city;
    }
    public String getEventType(){
        return eventType;
    }
    public int getYear(){
        return year;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
