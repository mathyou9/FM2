package Services;

import Dao.DataAccessException;
import Dao.Event_DAO;
import Dao.Person_DAO;
import Dao.User_DAO;
import Model.Event_Model;
import Model.Person_Model;
import Model.User_Model;
import Requests.Load_Request;
import Requests.Login_Request;
import Results.Load_Results;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * service that takes care of load requests
 */
public class Load_Service {
    private int numPersonsAdded;
    private int numUsersAdded;
    private int numEventsAdded;

    private String dbName = "database.db";
    private String connectionURL = "jdbc:sqlite:" + dbName;

    User_DAO userDao = new User_DAO();
    Event_DAO eventDao = new Event_DAO();
    Person_DAO personDao = new Person_DAO();
    Connection connection = null;

    public Load_Results loadResults(Load_Request loadRequest){
        numPersonsAdded = 0;
        numUsersAdded = 0;
        numEventsAdded = 0;
        try {
            connection = DriverManager.getConnection(connectionURL);
            userDao.deleteAllUsers(connection);
            eventDao.deleteAllEvents(connection);
            personDao.deleteAllPersons(connection);
            for(User_Model user : loadRequest.users){
                userDao.insert(connection, user);
                numUsersAdded++;
            }
            for(Event_Model event : loadRequest.events){
                eventDao.insert(connection, event);
                numEventsAdded++;
            }
            for(Person_Model person : loadRequest.persons){
                personDao.insert(connection, person);
                numPersonsAdded++;
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }


        return new Load_Results("Successfully added " + numUsersAdded + " users, " + numPersonsAdded + " persons, and " + numEventsAdded + " events to the database." );
    }
}
