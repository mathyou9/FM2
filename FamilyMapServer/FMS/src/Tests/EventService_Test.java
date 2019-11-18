package Tests;

import Dao.AuthorizationToken_DAO;
import Dao.DataAccessException;
import Model.AuthorizationToken_Model;
import Model.User_Model;
import Requests.Login_Request;
import Results.Clear_Results;
import Results.Event_Results;
import Results.Login_Results;
import Results.Register_Results;
import Services.Clear_Service;
import Services.Event_Service;
import Services.Login_Service;
import Services.Register_Service;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EventService_Test {
    @Test
    public void EventByTokenPass(){
        Register_Service registerService = new Register_Service();
        try {
            Register_Results registerResults = registerService.registerService(new User_Model(null, "username", "password", "email", "firstName", "lastName", "m"));
            String authToken = registerResults.getAuthorizationToken();

            Event_Service eventService = new Event_Service();
            Event_Results eventResults = eventService.getEventByToken(authToken);

            Gson gson = new Gson();
            String json = gson.toJson(eventResults);
            Assertions.assertNotNull(json);

        } catch (SQLException | DataAccessException e) {
            Assertions.fail();
            e.printStackTrace();
        }


    }
    @Test
    public void EventByTokenFail(){
        Register_Service registerService = new Register_Service();
        try {
            Register_Results registerResults = registerService.registerService(new User_Model(null, "username", "password", "email", "firstName", "lastName", "m"));
            String authToken = registerResults.getAuthorizationToken();

            Clear_Service clearService = new Clear_Service();
            Clear_Results clearResults = clearService.clear();

            Event_Service eventService = new Event_Service();
            Event_Results eventResults = eventService.getEventByToken(authToken);

            Gson gson = new Gson();
            String json = gson.toJson(eventResults);
            Assertions.assertEquals("{\"message\":\"Error invalid token\"}", json);

        } catch (SQLException | DataAccessException e) {
            Assertions.fail();
            e.printStackTrace();
        }
    }
}
