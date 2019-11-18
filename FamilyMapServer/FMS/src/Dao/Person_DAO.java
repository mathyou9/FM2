package Dao;

import Model.Person_Model;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Person_DAO {

    public static void main(String[] args){
        try{
            Person_DAO pdao = new Person_DAO();
            List<Person_Model> persons = pdao.doTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person_Model> doTransaction() throws SQLException{
        List<Person_Model> persons;
        Connection connection = null;
        String dbName = "database.db";
        String connectionURL = "jdbc:sqlite:" + dbName;
        connection = DriverManager.getConnection(connectionURL);
        persons = getAllPersons(connection);
        return persons;
    }

    public boolean insert(Connection connection, Person_Model p) throws DataAccessException {
        PreparedStatement stmt = null;
        String sql = "insert into Persons (PersonID, AssociatedUsername, FirstName, LastName, Gender, FatherID, MotherID, SpouseID) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, p.getPersonID());
            stmt.setString(2, p.getAssociatedUsername());
            stmt.setString(3, p.getFirstName());
            stmt.setString(4, p.getLastName());
            stmt.setString(5, p.getGender());
            stmt.setString(6, p.getFatherID());
            stmt.setString(7, p.getMotherID());
            stmt.setString(8, p.getSpouseID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting into the database");
        } finally {
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


    public Person_Model find(Connection connection, String ID){
        Person_Model thisPerson = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Persons WHERE PersonID = '" + ID + "'";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()){
                thisPerson = new Person_Model(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return thisPerson;
        }
    }


    public boolean remove(Connection connection, int ID){
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM Persons WHERE PersonID = " + Integer.toString(ID);
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Person_Model> getAllPersons(Connection connection){
        List<Person_Model> persons = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT PersonID, AssociatedUserName, FirstName, LastName FROM Persons";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                String userName = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                persons.add(new Person_Model(userName, firstName, lastName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }
    public ArrayList<Person_Model> getPersonsByAssociatedUserName(Connection connection, String uN){
        ArrayList<Person_Model> persons = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT PersonID, AssociatedUserName, FirstName, LastName, Gender, FatherID, MotherID, SpouseID FROM Persons WHERE AssociatedUserName = '" + uN + "'";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                String personID = rs.getString(1);
                String userName = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String Gender = rs.getString(5);
                String FatherID = rs.getString(6);
                String MotherID = rs.getString(7);
                String SpouseID = rs.getString(8);
                persons.add(new Person_Model(personID, userName, firstName, lastName, Gender, FatherID, MotherID, SpouseID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    public boolean deleteAllPersons(Connection connection){
        PreparedStatement stmt = null;
        String sql = "DELETE FROM Persons";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteUsingPersonID(Connection connection, String ID){
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM Persons WHERE PersonID = '" + ID + "'";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteUsingUsername(Connection connection, String username){
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM Persons WHERE AssociatedUsername = '" + username + "'";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
