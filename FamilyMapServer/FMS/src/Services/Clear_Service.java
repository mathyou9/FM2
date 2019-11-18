package Services;

import Dao.AuthorizationToken_DAO;
import Dao.Event_DAO;
import Dao.Person_DAO;
import Dao.User_DAO;
import Results.Clear_Results;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * clears services
 */
public class Clear_Service {

    private String dbName = "database.db";
    private String connectionURL = "jdbc:sqlite:" + dbName;

    public Clear_Results clear(){
        Connection connection = null;
        AuthorizationToken_DAO authDAO = new AuthorizationToken_DAO();
        User_DAO  userDao = new User_DAO();
        Event_DAO eventDao = new Event_DAO();
        Person_DAO personDao = new Person_DAO();
        try {
            connection = DriverManager.getConnection(connectionURL);
            authDAO.deleteAllTokens(connection);
            userDao.deleteAllUsers(connection);
            eventDao.deleteAllEvents(connection);
            personDao.deleteAllPersons(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Clear_Results("Clear Succeeded.");
    }
}
