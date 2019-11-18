package Services;

import Dao.AuthorizationToken_DAO;
import Dao.DataAccessException;
import Dao.Event_DAO;
import Model.Event_Model;
import Model.User_Model;
import Results.EventID_Results;
import Results.Event_Results;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * event service
 */
public class Event_Service {

    private String dbName = "database.db";
    private String connectionURL = "jdbc:sqlite:" + dbName;

    public Event_Results getEventByToken(String authToken) throws SQLException {
        Event_DAO eventDao = new Event_DAO();
        AuthorizationToken_DAO authDao = new AuthorizationToken_DAO();
        Connection connection = null;
        ArrayList<Event_Model> events = null;
        User_Model user = null;
            connection = DriverManager.getConnection(connectionURL);
            String userName = authDao.getUser(connection, authToken);
            if(userName == null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return new Event_Results("Error invalid token");
            }
            events = eventDao.findList(connection, userName);
        if(events == null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return new Event_Results("Error no events found.");
        }
        int eventSize = events.size();
        Event_Model[] eventArray = new Event_Model[eventSize];
        for(int i = 0; i < eventArray.length; i++){
            eventArray[i] = events.get(i);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Event_Results(eventArray);
    }
    public EventID_Results getEventByEventID(String ID, String authToken) throws SQLException {
        Event_DAO eventDao = new Event_DAO();
        AuthorizationToken_DAO authDao = new AuthorizationToken_DAO();
        Connection connection = null;
        Event_Model event = null;
        connection = DriverManager.getConnection(connectionURL);
        String userName = authDao.getUser(connection, authToken);

        event = eventDao.find(connection, ID);

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(userName == null){
            return new EventID_Results("Error invalid token");
        }
        if(event == null){
            return new EventID_Results("Error no event found");
        }
        if(!userName.equals(event.getAssociatedUsername())){
            return new EventID_Results("Error you are not authorized to view this");
        }
        return new EventID_Results(event.getAssociatedUsername(), event.getEventID(), event.getPersonID(), event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear());
    }
}
