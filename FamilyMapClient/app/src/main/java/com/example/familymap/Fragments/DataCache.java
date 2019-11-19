package com.example.familymap.Fragments;

import com.example.familymap.Models.Event_Model;
import com.example.familymap.Models.Person_Model;

import java.util.HashMap;
import java.util.Map;

public class DataCache {

    private Map<String, Person_Model> mapOfPersons;
    private Map<String, Event_Model> mapOfEvents;
    private String host;
    private int port;


    private static DataCache instance;

    public static DataCache getInstance() {
        if(instance == null) {
            instance = new DataCache();
        }

        return instance;
    }

    private DataCache() {

    }

    public void SetData(String host, int port, Person_Model[] persons, Event_Model[] events){
        mapOfEvents = new HashMap<>();
        mapOfPersons = new HashMap<>();
        this.host = host;
        this.port = port;
        for(Event_Model event : events){
            mapOfEvents.put(event.getEventID(), event);
        }
        for(Person_Model person : persons){
            mapOfPersons.put(person.getPersonID(), person);
        }
    }

    public Person_Model getPerson(String pID) {
        return mapOfPersons.get(pID);
    }
}