package Requests;

import Model.Event_Model;
import Model.Person_Model;
import Model.User_Model;

/**
 * loads up arrays of each users, events, and persons
 */
public class Load_Request {
    public User_Model[] users;
    public Event_Model[] events;
    public Person_Model[] persons;

    public User_Model[] getUsers(){
        return users;
    }
    public Event_Model[] getEvents(){
        return events;
    }
    public Person_Model[] getPersons(){
        return persons;
    }
    public void setUsers(User_Model[] users){
        this.users = users;
    }
    public void setEvents(Event_Model[] events){
        this.events = events;
    }
    public void setPersons(Person_Model[] persons){
        this.persons = persons;
    }
}
