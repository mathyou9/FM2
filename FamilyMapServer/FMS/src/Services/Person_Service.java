package Services;

import Dao.AuthorizationToken_DAO;
import Dao.Person_DAO;
import Model.AuthorizationToken_Model;
import Model.Person_Model;
import Model.User_Model;
import Results.PersonID_Results;
import Results.Person_Results;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * service that takes care of person requests
 */
public class Person_Service {
    private String dbName = "database.db";
    private String connectionURL = "jdbc:sqlite:" + dbName;

    ArrayList<Person_Model> familyMembers = null;
    Person_DAO personDao = null;
    Connection connection = null;

    public Person_Results getPersonsByToken(String authToken){
        AuthorizationToken_DAO authDao = new AuthorizationToken_DAO();
        User_Model user = null;
        familyMembers = new ArrayList<Person_Model>();
        personDao = new Person_DAO();
        try {
            connection = DriverManager.getConnection(connectionURL);
            String userName = authDao.getUser(connection, authToken);
            if(userName == null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return new Person_Results("Error not a valid token");
            }
            familyMembers = personDao.getPersonsByAssociatedUserName(connection, userName);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        int eventSize = familyMembers.size();
        Person_Model[] eventArray = new Person_Model[eventSize];
        for(int i = 0; i < eventArray.length; i++){
            eventArray[i] = familyMembers.get(i);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(eventArray == null){
            return new Person_Results("Error no persons associated with that token");
        }
        return new Person_Results(eventArray);
    }
    public PersonID_Results getPersonByPersonID(String ID, String authToken){
        Person_DAO personDao = new Person_DAO();
        AuthorizationToken_DAO authDao = new AuthorizationToken_DAO();
        AuthorizationToken_Model authT = null;
        Connection connection = null;
        Person_Model person = null;
        String username = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            username = authDao.getUser(connection, authToken);
            person = personDao.find(connection, ID);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(username == null){
            return new PersonID_Results("Error no permissions");
        }
        if(person == null){
            return new PersonID_Results("Error no person associated with that username");
        }
        if(!username.equals(person.getAssociatedUsername())){
            return new PersonID_Results("Error no permissions");
        }

        return new PersonID_Results(person.getAssociatedUsername(), person.getPersonID(), person.getFirstName(), person.getLastName(), person.getGender(), person.getFatherID(), person.getMotherID(), person.getSpouseID());
    }
}
