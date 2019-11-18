package Tests;

import Dao.DataAccessException;
import Model.User_Model;
import Requests.Load_Request;
import Requests.Login_Request;
import Results.*;
import Services.*;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;

public class LoadService_Test {

    @Test
    public void LoadPass(){
        Register_Service registerService = new Register_Service();
        try {
            Clear_Service clearService = new Clear_Service();
            clearService.clear();

            Gson gson = new Gson();
            Load_Service loadService = new Load_Service();
            FillData fillData = new FillData();
            Load_Request loadRequest = gson.fromJson(fillData.getString(), Load_Request.class);

            Load_Results loadResults = loadService.loadResults(loadRequest);

            Login_Request loginRequest = new Login_Request();
            loginRequest.setUserName("sheila");
            loginRequest.setPassword("parker");
            Login_Service loginService = new Login_Service();
            Login_Results loginResults = loginService.DoService(loginRequest);



            Event_Service eventService = new Event_Service();
            Event_Results eventResults = eventService.getEventByToken(loginResults.getToken());

            String json = gson.toJson(eventResults);
            Assertions.assertNotNull(json);
            Assertions.assertEquals(16, eventResults.getData().length);

        } catch (SQLException e) {
            Assertions.fail();
            e.printStackTrace();
        }


    }
    @Test
    public void LoadFill2(){
        Register_Service registerService = new Register_Service();
        try {
            Clear_Service clearService = new Clear_Service();
            clearService.clear();

            Gson gson = new Gson();
            Load_Service loadService = new Load_Service();
            FillData fillData = new FillData();
            Load_Request loadRequest = gson.fromJson(fillData.getString(), Load_Request.class);

            Load_Results loadResults = loadService.loadResults(loadRequest);

            Login_Request loginRequest = new Login_Request();
            loginRequest.setUserName("patrick");
            loginRequest.setPassword("spencer");
            Login_Service loginService = new Login_Service();
            Login_Results loginResults = loginService.DoService(loginRequest);

            Person_Service personService = new Person_Service();
            Person_Results personResults = personService.getPersonsByToken(loginResults.getToken());

            String json = gson.toJson(personResults);
            Assertions.assertNotNull(json);
            Assertions.assertEquals(3, personResults.getData().length);

        } catch (Throwable e) {
            Assertions.fail();
            e.printStackTrace();
        }
    }
}
