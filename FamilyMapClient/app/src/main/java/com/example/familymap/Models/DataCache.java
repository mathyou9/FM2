package com.example.familymap.Models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataCache {



    private Map<String, Person_Model> mapOfPersons;
    private Map<String, Event_Model> mapOfEvents;
    private Set<String> eventTypes;
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
        eventTypes = new HashSet<>();
        this.host = host;
        this.port = port;
        for(Event_Model event : events){
            mapOfEvents.put(event.getEventID(), event);
            eventTypes.add(event.getEventType().toUpperCase());
        }
        for(Person_Model person : persons){
            mapOfPersons.put(person.getPersonID(), person);
        }
    }

    public Person_Model getPerson(String pID) {
        return mapOfPersons.get(pID);
    }
    public Map<String, Event_Model> getAllEvents(){return mapOfEvents;};

    public Set<String> getEventTypes(){
        return eventTypes;
    }

    public Event_Model getEvents(String eventID) {
        return mapOfEvents.get(eventID);
    }
}