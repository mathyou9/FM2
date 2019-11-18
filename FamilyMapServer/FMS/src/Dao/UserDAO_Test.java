package Dao;

import Model.User_Model;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserDAO_Test {

    private User_DAO user = new User_DAO();
    Connection connection = null;
    String dbName = "database.db";
    String connectionURL = "jdbc:sqlite:" + dbName;

    @Test
    public void insertPass(){
        User_Model compareTest = null;
        User_Model thisUser = new User_Model("1", "fakeUser", "fakePassword", "fakeEmail", "firstName", "lastName", "M");

        try {
            connection = DriverManager.getConnection(connectionURL);
            user.deleteAllUsers(connection);
            user.insert(connection, thisUser);
            compareTest = user.find(connection, thisUser.getUserName());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataAccessException e){

        }
        Assertions.assertEquals(thisUser.getPersonID(), compareTest.getPersonID());
        Assertions.assertEquals(thisUser.getUserName(), compareTest.getUserName());
        Assertions.assertEquals(thisUser.getFirstName(), compareTest.getFirstName());
        Assertions.assertEquals(thisUser.getLastName(), compareTest.getLastName());
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    //duplicate entry
    public void insertFail(){
        User_Model compareTest = null;
        User_Model thisUser = new User_Model("1", "fakeUser", "fakePassword", "fakeEmail", "firstName", "lastName", "M");
        boolean didItWork = true;
        try {
            connection = DriverManager.getConnection(connectionURL);
            user.deleteAllUsers(connection);
            user.insert(connection, thisUser);
            user.insert(connection, thisUser);
        } catch (SQLException e) {
            e.printStackTrace();
            didItWork = false;
        } catch (DataAccessException e){
            didItWork = false;
        }
        Assertions.assertFalse(didItWork);
        compareTest = thisUser;
        try {
            connection = DriverManager.getConnection(connectionURL);
            compareTest = user.find(connection, thisUser.getUserName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(compareTest);
    }
    @Test
    public void findPass(){
        User_Model thisUser = new User_Model("1", "fakeUser", "fakePassword", "fakeEmail", "firstName", "lastName", "M");
        try {
            connection = DriverManager.getConnection(connectionURL);
            user.deleteAllUsers(connection);
            user.insert(connection, thisUser);
            Assertions.assertEquals(thisUser.getPersonID(), user.find(connection, thisUser.getUserName()).getPersonID() );
            Assertions.assertEquals(thisUser.getFirstName(), user.find(connection, thisUser.getUserName()).getFirstName() );
            Assertions.assertEquals(thisUser.getLastName(), user.find(connection, thisUser.getUserName()).getLastName() );
            Assertions.assertEquals(thisUser.getPersonID(), user.find(connection, thisUser.getUserName()).getPersonID() );
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void findFail(){
        User_Model thisUser = new User_Model("1", "fakeUser", "fakePassword", "fakeEmail", "firstName", "lastName", "M");
        try {
            connection = DriverManager.getConnection(connectionURL);
            user.deleteAllUsers(connection);
            Assertions.assertNull(user.find(connection, thisUser.getUserName()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void deleteAllPass(){
        try {
            connection = DriverManager.getConnection(connectionURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(user.deleteAllUsers(connection), true);
    }
    @Test
    public void deleteAllFail(){
        User_Model compareTest = null;
        User_Model thisUser = new User_Model("1", "fakeUser", "fakePassword", "fakeEmail", "firstName", "lastName", "M");

        try {
            connection = DriverManager.getConnection(connectionURL);
            Assertions.assertEquals(user.deleteAllUsers(connection), true);
            Assertions.assertEquals(null, user.find(connection, thisUser.getUserName()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void insertUser(){
        User_Model insertUser = new User_Model("fake1", "firstName", "lastName");
        try {
            connection = DriverManager.getConnection(connectionURL);
            Assertions.assertEquals(user.insert(connection, insertUser), true);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void findUser(){
        String userID = "2";
        try {
            connection = DriverManager.getConnection(connectionURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertSame(user.find(connection, userID).getPersonID(), userID);
    }

    //@Test
    public void deleteUser(){

    }
    //@AfterAll
    //@Test
    public void deleteAllUsers(){
        try {
            connection = DriverManager.getConnection(connectionURL);
            Assertions.assertEquals(user.deleteAllUsers(connection), true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
