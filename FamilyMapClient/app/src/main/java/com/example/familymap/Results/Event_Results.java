package com.example.familymap.Results;


import com.example.familymap.Models.Event_Model;

/**
 * gets events
 */
public class Event_Results {
    private Event_Model[] data;
    private String message;
    public Event_Model[] getEvent(){
        return data;
    }
    public void setEvents(Event_Model[] event){
        this.data = event;
    }
    public Event_Results(Event_Model[] eventArray){
        this.data = eventArray;
    }
    public Event_Results(String message){
        this.message = message;
    }
    public Event_Model[] getData(){
        return data;
    }
}
