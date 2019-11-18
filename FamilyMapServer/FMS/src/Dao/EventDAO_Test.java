package Dao;

import Model.Event_Model;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EventDAO_Test {
    private Event_DAO event = new Event_DAO();
    Connection connection = null;
    String dbName = "database.db";
    String connectionURL = "jdbc:sqlite:" + dbName;

    @Test
    public void insertPass(){
        Event_Model eventFound = null;
        Event_Model eventModel = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            event.deleteAllEvents(connection);
            eventModel = new Event_Model("eid", "username", "pid", 0, 0, "usa", "city", "eventtype", 2000);
            event.insert(connection, eventModel);
            eventFound = event.find(connection, "eid");

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(eventModel.getAssociatedUsername(), eventFound.getAssociatedUsername());
        Assertions.assertEquals(eventModel.getCity(), eventFound.getCity());
        Assertions.assertEquals(eventModel.getEventID(), eventFound.getEventID());
        Assertions.assertEquals(eventModel.getPersonID(), eventFound.getPersonID());
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void insertFail(){
        Event_Model eventFound = null;
        Event_Model eventModel = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            event.deleteAllEvents(connection);
            eventModel = new Event_Model("eid", "username", "pid", 0, 0, "usa", "city", "eventtype", 2000);
            event.insert(connection, eventModel);
            event.insert(connection, eventModel);
            event.insert(connection, eventModel);
            eventFound = event.find(connection, "eid");

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertNull(eventFound);
    }
    @Test
    public void findPass(){
        Event_Model eventFound = null;
        Event_Model eventModel = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            event.deleteAllEvents(connection);
            eventModel = new Event_Model("eid", "username", "pid", 0, 0, "usa", "city", "eventtype", 2000);
            event.insert(connection, eventModel);
            eventFound = event.find(connection, "eid");

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(eventModel.getAssociatedUsername(), eventFound.getAssociatedUsername());
        Assertions.assertEquals(eventModel.getCity(), eventFound.getCity());
        Assertions.assertEquals(eventModel.getEventID(), eventFound.getEventID());
        Assertions.assertEquals(eventModel.getPersonID(), eventFound.getPersonID());
    }
    @Test
    public void findFail(){
        Event_Model eventFound = null;
        Event_Model eventModel = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            event.deleteAllEvents(connection);
            eventModel = new Event_Model("eid", "username", "pid", 0, 0, "usa", "city", "eventtype", 2000);

            eventFound = event.find(connection, "eid");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertNull(eventFound);
    }
    @Test
    public void deleteAllPass(){
        Event_Model eventFound = null;
        Event_Model eventModel = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            event.deleteAllEvents(connection);
            eventModel = new Event_Model("eid", "username", "pid", 0, 0, "usa", "city", "eventtype", 2000);
            event.insert(connection, eventModel);
            event.deleteAllEvents(connection);
            eventFound = event.find(connection, "eid");

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertNull(eventFound);
    }
    @Test
    public void deleteAllPass2(){
        Event_Model eventFound = null;
        Event_Model eventModel = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            event.deleteAllEvents(connection);
            eventModel = new Event_Model("eid", "username", "pid", 0, 0, "usa", "city", "eventtype", 2000);
            event.insert(connection, eventModel);
            event.deleteAllEvents(connection);
            event.insert(connection, eventModel);
            event.deleteAllEvents(connection);
            event.insert(connection, eventModel);
            event.deleteAllEvents(connection);
            eventFound = event.find(connection, "eid");

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertNull(eventFound);
    }
}
