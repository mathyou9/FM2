package Services;

import Dao.*;
import Model.AuthorizationToken_Model;
import Model.Event_Model;
import Model.Person_Model;
import Model.User_Model;
import Results.Fill_Results;
import Results.Register_Results;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

/**
 * service that takes care of register requests
 */
public class Register_Service {
    private String authToken;
    private String personID;
    private Database db;
    private String dbName = "database.db";
    private String connectionURL = "jdbc:sqlite:" + dbName;
    Connection connection = null;

    public Register_Results registerService(User_Model user) throws SQLException, DataAccessException {
        //db = new Database();
        User_DAO userDao = new User_DAO();
        Person_DAO personDao = new Person_DAO();
        User_Model tempUser = null;
        AuthorizationToken_Model authToken = new AuthorizationToken_Model();
            //db.open();
            connection = DriverManager.getConnection(connectionURL);
            user.setPersonID(UUID.randomUUID().toString().substring(0,8));
            if(user.getGender().length() == 1 && (user.getGender().charAt(0) == 'm' || user.getGender().charAt(0) == 'f')){
                User_Model userModel = userDao.find(connection, user.getUserName());
                if(userModel != null){
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return new Register_Results("Error you cannot register more than once");
                }
                userDao.insert(connection, user);

                createEvent(user);

                Person_Model person = new Person_Model(user);
                personDao.insert(connection, person);

                authToken.setAuthorizationToken(UUID.randomUUID().toString().substring(0,8));
                authToken.setUserName(user.getUserName());
                AuthorizationToken_DAO authDao = new AuthorizationToken_DAO();
                authDao.createToken(connection, authToken);

                tempUser = userDao.find(connection, user.getUserName());
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Fill_Service fillService = new Fill_Service();
                Fill_Results fillResults = fillService.fillResults(user.getUserName(), 4);
            } else {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return new Register_Results("Error you need to specify male or female with m or f.");
            }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Register_Results(authToken.getAuthorizationToken(), tempUser.getUserName(), tempUser.getPersonID());
    }

    private void createEvent(User_Model user) throws DataAccessException {
        String EventID = UUID.randomUUID().toString().substring(0,8);
        String AssociatedUsername = user.getUserName();
        String PersonID = user.getPersonID();
        int Latitude = 50;
        int Longitude = 50;
        String Country = "United States";
        String City = "Provo";
        String EventType = "Birth";
        int Year = 1997;
        Event_DAO eventDao = new Event_DAO();
        Event_Model event = new Event_Model(EventID, AssociatedUsername, PersonID, Latitude, Longitude, Country, City, EventType, Year);
        eventDao.insert(connection, event);
    }

}
