package Dao;

import Model.Person_Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO_Test {

    private Person_DAO person = new Person_DAO();
    Connection connection = null;
    String dbName = "database.db";
    String connectionURL = "jdbc:sqlite:" + dbName;

    @Test
    public void insertPass(){
        Person_Model compareTest = null;
        Person_Model thisPerson = new Person_Model("1","fakeUser", "fakeFirst","fakeLast", "M", "fakeFatherID", "fakeMotherID", "fakeSpouseID");

        try {
            connection = DriverManager.getConnection(connectionURL);
            person.deleteAllPersons(connection);
            person.insert(connection, thisPerson);
            compareTest = person.find(connection, thisPerson.getPersonID());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataAccessException e){

        }
        Assertions.assertEquals(thisPerson.getPersonID(), compareTest.getPersonID());
        Assertions.assertEquals(thisPerson.getAssociatedUsername(), compareTest.getAssociatedUsername());
        Assertions.assertEquals(thisPerson.getFirstName(), compareTest.getFirstName());
        Assertions.assertEquals(thisPerson.getLastName(), compareTest.getLastName());
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertFail(){
        Person_Model compareTest = null;
        Person_Model thisPerson = new Person_Model("1","fakeUser", "fakeFirst","fakeLast", "M", "fakeFatherID", "fakeMotherID", "fakeSpouseID");
        boolean didItWork = true;
        try {
            connection = DriverManager.getConnection(connectionURL);
            person.deleteAllPersons(connection);
            person.insert(connection, thisPerson);
            person.insert(connection, thisPerson);

        } catch (SQLException e) {
            e.printStackTrace();
            didItWork = false;
        } catch (DataAccessException e){
            didItWork = false;
        }
        Assertions.assertFalse(didItWork);
        compareTest = thisPerson;
        try {
            connection = DriverManager.getConnection(connectionURL);
            compareTest = person.find(connection, thisPerson.getPersonID());
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
    public void deleteAllPass(){
        try {
            connection = DriverManager.getConnection(connectionURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(person.deleteAllPersons(connection), true);
    }
    @Test
    public void deleteAllFail(){
        //cannot find a user
        Person_Model compareTest = null;
        Person_Model thisPerson = new Person_Model("1","fakeUser", "fakeFirst","fakeLast", "M", "fakeFatherID", "fakeMotherID", "fakeSpouseID");
        try {
            connection = DriverManager.getConnection(connectionURL);
            Assertions.assertEquals(person.deleteAllPersons(connection), true);
            Assertions.assertEquals(null, person.find(connection, thisPerson.getPersonID()));
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
    public void findPass(){
        Person_Model thisPerson = new Person_Model("1","fakeUser", "fakeFirst","fakeLast", "M", "fakeFatherID", "fakeMotherID", "fakeSpouseID");
        try {
            connection = DriverManager.getConnection(connectionURL);
            person.deleteAllPersons(connection);
            person.insert(connection, thisPerson);
            Assertions.assertEquals(thisPerson.getPersonID(), person.find(connection, thisPerson.getPersonID()).getPersonID() );
            Assertions.assertEquals(thisPerson.getFirstName(), person.find(connection, thisPerson.getPersonID()).getFirstName() );
            Assertions.assertEquals(thisPerson.getLastName(), person.find(connection, thisPerson.getPersonID()).getLastName() );
            Assertions.assertEquals(thisPerson.getAssociatedUsername(), person.find(connection, thisPerson.getPersonID()).getAssociatedUsername() );
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
    //tests for finding without the object in the database
    public void findFail(){
        Person_Model thisPerson = new Person_Model("1","fakeUser", "fakeFirst","fakeLast", "M", "fakeFatherID", "fakeMotherID", "fakeSpouseID");
        try {
            connection = DriverManager.getConnection(connectionURL);
            person.deleteAllPersons(connection);
            Assertions.assertNull(person.find(connection, thisPerson.getPersonID()));
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
    public void testSinglePersonAdd(){
        Person_Model singlePerson = new Person_Model("fake", "firstName", "lastName");
        try {
            connection = DriverManager.getConnection(connectionURL);
            Assertions.assertEquals(person.insert(connection, singlePerson), true);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    //@Test
    public void testFivePersonAdd(){
        List<Person_Model> personList = new ArrayList<Person_Model>();
        for(int i = 0; i < 5; i++){
            personList.add(new Person_Model(Integer.toString(1+i),"fakeUser"+i, "fakeFirst"+i,"fakeLast"+i, "M", "fakeFatherID"+i, "fakeMotherID"+i, "fakeSpouseID"+i));
        }
        try {
            connection = DriverManager.getConnection(connectionURL);
            for(Person_Model p : personList){
                Assertions.assertEquals(person.insert(connection, p), true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    //@Test
    public void testFiftyPersonAdd(){
        List<Person_Model> personList = new ArrayList<Person_Model>();
        for(int i = 0; i < 50; i++){
            personList.add(new Person_Model(Integer.toString(1+i),"fakeUser"+i, "fakeFirst"+i,"fakeLast"+i, "M", "fakeFatherID"+i, "fakeMotherID"+i, "fakeSpouseID"+i));
        }
        try {
            connection = DriverManager.getConnection(connectionURL);
            for(Person_Model p : personList){
                Assertions.assertEquals(person.insert(connection, p), true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void getAllPersonsShouldReturnAllPersons(){
        try {
            connection = DriverManager.getConnection(connectionURL);
            Assertions.assertEquals(person.getAllPersons(connection), 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //@Test
    public void insertPerson(){
        Person_Model singlePerson = new Person_Model("fake", "firstName", "lastName");
        try {
            connection = DriverManager.getConnection(connectionURL);
            Assertions.assertEquals(person.insert(connection, singlePerson), true);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    //@Test
    public void findPerson(){
        String personID = "1";
        try {
            connection = DriverManager.getConnection(connectionURL);
            Assertions.assertSame(person.find(connection, personID).getPersonID(), personID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //@Test
    public void deletePerson(){
        int personID = 2;
        try {
            connection = DriverManager.getConnection(connectionURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(person.remove(connection, personID),  true);
    }
    //@Test
    public void deleteAllPersons(){
        try {
            connection = DriverManager.getConnection(connectionURL);
            Assertions.assertEquals(person.deleteAllPersons(connection), true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
