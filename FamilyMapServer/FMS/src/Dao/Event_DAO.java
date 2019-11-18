package Dao;

import Model.Event_Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Event_DAO {
    public static void main(String[] args){
        try{
            Event_DAO edao = new Event_DAO();
            List<Event_Model> events = edao.doTransaction();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Event_Model> doTransaction() throws SQLException{
        List<Event_Model> events;
        Connection connection = null;
        String dbName = "database.db";
        String connectionURL = "jdbc:sqlite:" + dbName;
        connection = DriverManager.getConnection(connectionURL);
        events = getAllEvents(connection);
        return events;
    }
    public boolean insert(Connection connection, Event_Model event) throws DataAccessException {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO Events (EventID, AssociatedUserName, PersonID, Latitude, Longitude, Country, City, EventType, Year) values (?,?,?,?,?,?,?,?,?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setDouble(4, event.getLatitude());
            stmt.setDouble(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting into the database");
        } finally{
            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    public Event_Model find(Connection connection, String eventID){
        Event_Model event = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Events WHERE EventID = '" + eventID + "'";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()){
                event = new Event_Model(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            return event;
        }
    }
    public ArrayList<Event_Model> findList(Connection connection, String userName){
        ArrayList<Event_Model> events = new ArrayList<Event_Model>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Events WHERE AssociatedUsername = '" + userName + "'";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                events.add(new Event_Model(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            return events;
        }
    }
    public Event_Model findWithUserID(String userID){
        return null;
    }

    public void remove(){}

    public boolean deleteAllUsingPersonID(Connection connection, String ID){
        Event_Model thisEvent = null;
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM Events WHERE PersonID = '" + ID + "'";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteAllUsingUsername(Connection connection, String username){
        Event_Model thisEvent = null;
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM Events WHERE AssociatedUsername = '" + username + "'";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAllEvents(Connection connection){
        PreparedStatement stmt = null;
        String sql = "DELETE FROM Events";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Event_Model> getAllEvents(Connection connection){
        List<Event_Model> persons = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT EventID, AssociatedUserName, PersonID, Latitude, Longitude, Country, City, EventType, Year FROM Events";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                String eventID = rs.getString(1);
                String associatedUserName = rs.getString(2);
                String personID = rs.getString(3);
                double latitude = rs.getDouble(4);
                double longitude = rs.getDouble(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String eventType = rs.getString(8);
                int year = rs.getInt(9);
                persons.add(new Event_Model(eventID, associatedUserName, personID, latitude, longitude, country, city, eventType, year));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

}
