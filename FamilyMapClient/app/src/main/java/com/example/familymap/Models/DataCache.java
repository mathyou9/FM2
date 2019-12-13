package com.example.familymap.Models;

import com.example.familymap.Activities.SearchActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataCache {

//write 2 pass test cases for each function

    private Map<String, Person_Model> mapOfPersons;
    private Map<String, Event_Model> mapOfEvents;
    private Map<String, Event_Model[]> personsEvents;
    private Set<String> eventTypes;
    private String host;
    private int port;
    private Person_Model[] personArray;
    private Event_Model[] eventArray;
    private Person_Model user;
    private boolean isFromSettings = false;

    private ArrayList<String> motherSideIDs;
    private ArrayList<String> fatherSideIDs;


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
        eventArray = mapOfEvents.values().toArray(new Event_Model[0]);
        personArray = mapOfPersons.values().toArray(new Person_Model[0]);
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

    public Map<String, Person_Model> getPersonMap() {
        return mapOfPersons;
    }

    public void setPersonsEvents() {
        personsEvents = new HashMap<>();
        for (Person_Model person : personArray) { //ATTEMPT TO GET LENGTH OF NULL ARRAY
            ArrayList<Event_Model> eventList = new ArrayList<>();
            for (Event_Model event : eventArray) {
                if (person.getPersonID().equals(event.getPersonID())) {
                    eventList.add(event);
                }
            }
            Object[] objects = eventList.toArray();
            Event_Model[] array = new Event_Model[objects.length];
            for (int k = 0; k < objects.length; k++) {
                array[k] = (Event_Model) objects[k];
            }
            sortEventList(array);
            personsEvents.put(person.getPersonID(), array);
        }
    }

    private void sortEventList(Event_Model[] e) {
        Event_Model temp;
        for (int i = 1; i < e.length; i++) {
            for (int j = i; j > 0; j--) {
                if (e[j].getYear() < e[j - 1].getYear()) {
                    temp = e[j];
                    e[j] = e[j - 1];
                    e[j - 1] = temp;
                }
            }
        }
    }

    public Person_Model[] getPersonArray(){
        return personArray;
    }
    public Event_Model[] getEventArray(){
        return eventArray;
    }

    public Map<String, Event_Model[]> getEventsForIndividual() {
        return personsEvents;
    }
    public void setUser(Person_Model person){
        user = person;
    }
    public Person_Model getUser(){
        return user;
    }
    public void fromSettings(boolean isFromSettings){
        this.isFromSettings = isFromSettings;
    }

    public ArrayList<String> getMothersSideIDs() {
        return motherSideIDs;
    }
    public void setMotherSideIDs(ArrayList<String> momSideIDs) {
        this.motherSideIDs = momSideIDs;

    }

    public ArrayList<String> getFatherSideIDs() {
        return fatherSideIDs;
    }
    public void setFatherSideIDs(ArrayList<String> fatherSideIDs) {
        this.fatherSideIDs = fatherSideIDs;

    }

    public ArrayList<SearchActivity.SearchResult> getSearchResults(String query) {
        String q = query.trim().toLowerCase();
        ArrayList<SearchActivity.SearchResult> results = new ArrayList<>();
        //search all people for names
        for (Person_Model p : personArray) {
            String last = p.getLastName().toLowerCase();
            String first = p.getFirstName().toLowerCase();
            if (first.contains(q) || last.contains(q)) {
                results.add(new SearchActivity.SearchResult(true, p.getPersonID()));
            }
        }
        //search all events for names
        for (Event_Model e : eventArray) {
            Person_Model p = getPerson(e.getPersonID());
            String last = p.getLastName().toLowerCase();
            String first = p.getFirstName().toLowerCase();
            if (first.contains(query.toLowerCase()) || last.contains(query.toLowerCase())) {
                results.add(new SearchActivity.SearchResult(false, e.getEventID()));
            }
        }
        return results;
    }


    public void logout(){
        mapOfPersons = null;
        mapOfEvents = null;
        personsEvents = null;
        eventTypes = null;
        host = null;
        port = 0;
        personArray = null;
        eventArray = null;
        user = null;

        motherSideIDs = null;
        fatherSideIDs = null;
    }
}