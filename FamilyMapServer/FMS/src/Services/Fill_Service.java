package Services;

import Dao.DataAccessException;
import Dao.Event_DAO;
import Dao.Person_DAO;
import Dao.User_DAO;
import Model.Event_Model;
import Model.Person_Model;
import Model.User_Model;
import Results.Fill_Results;
import jdk.jfr.Event;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * service that takes care of the fill requests
 * 
 */
public class Fill_Service {
    int numPersonsAdded = 0;
    int numEventsAdded = 0;

    private String dbName = "database.db";
    private String connectionURL = "jdbc:sqlite:" + dbName;

    User_DAO userDao = new User_DAO();
    Event_DAO eventDao = new Event_DAO();
    Person_DAO personDao = new Person_DAO();
    Connection connection = null;

    private int totalGeneration;

    private String currentUser;

    public Fill_Results fillResults(String username, int generations){
        totalGeneration = generations;
        currentUser = username;
        userDao = new User_DAO();
        eventDao = new Event_DAO();
        personDao = new Person_DAO();
        User_Model userModel = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            userModel = userDao.find(connection, username);
            if(userModel == null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return new Fill_Results("Error no user");
            }
            userDao.remove(connection, userModel.getPersonID());
            eventDao.deleteAllUsingUsername(connection, userModel.getUserName());
            personDao.deleteUsingUsername(connection, userModel.getUserName());
            userDao.insert(connection, userModel);
            Person_Model person = new Person_Model(userModel);
            personDao.insert(connection, person);
            numPersonsAdded++;
            createBirth(person, 0);
            fillParents(generations, person);
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Fill_Results("Successfully added " + numPersonsAdded + " persons and " + numEventsAdded + " events to the database.");
    }

    private void fillParents(int generations, Person_Model child) throws DataAccessException {
        String dadPersonID = UUID.randomUUID().toString().substring(0,8);
        String dadFirstName = "Father";
        String lastName = "LastName";
        String dadGender = "m";
        String momPersonID = UUID.randomUUID().toString().substring(0,8);
        String momFirstName = "Mother";
        String momGender = "f";
        child.setFatherID(dadPersonID);
        child.setMotherID(momPersonID);
        personDao.deleteUsingPersonID(connection, child.getPersonID());
        personDao.insert(connection, child);

        Person_Model dad = new Person_Model(dadPersonID, currentUser, dadFirstName, lastName, dadGender, "", "", momPersonID);
        Person_Model mom = new Person_Model(momPersonID, currentUser, momFirstName, lastName, momGender, "", "", dadPersonID);
        personDao.insert(connection, dad);
        createEvents(dad, generations);
        personDao.insert(connection, mom);
        createEvents(mom, generations);
        numPersonsAdded++;
        numPersonsAdded++;
        if(generations > 1){
            fillParents(generations - 1, dad);
            fillParents(generations - 1, mom);
        }
        return;
    }

    private void createEvents(Person_Model person, int generation) throws DataAccessException {
        int age = (totalGeneration+1-generation)*25;
        createBirth(person, age);
        numEventsAdded++;
        age = age - 25;
        createMarriageEvent(person, age);
        numEventsAdded++;
        age = age - 50;
        createDeathEvent(person, age);
        numEventsAdded++;
    }
    private void createBirth(Person_Model person, int age) throws DataAccessException {
        String EventID = UUID.randomUUID().toString().substring(0,8);
        String AssociatedUsername = person.getAssociatedUsername();
        String PersonID = person.getPersonID();
        int Latitude = 100;
        int Longitude = 100;
        String Country = "United States";
        String City = "Provo";
        String EventType = "Birth";
        int Year = 1997 - age;
        Event_DAO eventDao = new Event_DAO();
        Event_Model event = new Event_Model(EventID, AssociatedUsername, PersonID, Latitude, Longitude, Country, City, EventType, Year);
        eventDao.insert(connection, event);
    }
    private void createMarriageEvent(Person_Model person, int age) throws DataAccessException {
        String EventID = UUID.randomUUID().toString().substring(0,8);
        String AssociatedUsername = person.getAssociatedUsername();
        String PersonID = person.getPersonID();
        int Latitude = 100;
        int Longitude = 100;
        String Country = "United States";
        String City = "Provo";
        String EventType = "Marriage";
        int Year = 1997 - age;
        Event_DAO eventDao = new Event_DAO();
        Event_Model event = new Event_Model(EventID, AssociatedUsername, PersonID, Latitude, Longitude, Country, City, EventType, Year);
        eventDao.insert(connection, event);
    }
    private void createDeathEvent(Person_Model person, int age) throws DataAccessException {
        String EventID = UUID.randomUUID().toString().substring(0,8);
        String AssociatedUsername = person.getAssociatedUsername();
        String PersonID = person.getPersonID();
        int Latitude = 100;
        int Longitude = 100;
        String Country = "United States";
        String City = "Provo";
        String EventType = "Death";
        int Year = 1997 - age;
        Event_DAO eventDao = new Event_DAO();
        Event_Model event = new Event_Model(EventID, AssociatedUsername, PersonID, Latitude, Longitude, Country, City, EventType, Year);
        eventDao.insert(connection, event);
    }
}
