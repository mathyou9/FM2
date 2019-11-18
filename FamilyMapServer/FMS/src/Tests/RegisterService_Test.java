package Tests;

import Dao.DataAccessException;
import Model.User_Model;
import Requests.Login_Request;
import Results.Login_Results;
import Results.Register_Results;
import Services.Clear_Service;
import Services.Login_Service;
import Services.Register_Service;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;

public class RegisterService_Test {
    @Test
    public void RegisterPass(){
        Register_Service registerService = new Register_Service();
        try {
            Clear_Service clearService = new Clear_Service();
            clearService.clear();

            Register_Results registerResults = registerService.registerService(new User_Model(null, "username", "password", "email", "firstName", "lastName", "m"));
            String authToken = registerResults.getAuthorizationToken();

            Login_Request loginRequest = new Login_Request();
            loginRequest.setUserName("username");
            loginRequest.setPassword("password");
            Login_Service loginService = new Login_Service();
            Login_Results loginResults = loginService.DoService(loginRequest);

            Gson gson = new Gson();
            String json = gson.toJson(loginResults);
            Assertions.assertNotNull(json);
            Assertions.assertEquals("username", loginResults.getUserName());

        } catch (SQLException | DataAccessException e) {
            Assertions.fail();
            e.printStackTrace();
        }


    }
    @Test
    public void RegisterFail(){
        Register_Service registerService = new Register_Service();
        try {
            Clear_Service clearService = new Clear_Service();
            clearService.clear();

            Register_Results registerResults = registerService.registerService(new User_Model(null, "username", "password", "email", "firstName", "lastName", "m"));

            Register_Results registerResults1 = registerService.registerService(new User_Model(null, "username", "password", "email", "firstName", "lastName", "m"));

            Gson gson = new Gson();
            String json = gson.toJson(registerResults1);
            Assertions.assertNotNull(json);
            Assertions.assertEquals("{\"message\":\"Error you cannot register more than once\"}", json);

        } catch (SQLException | DataAccessException e) {
            Assertions.fail();
            e.printStackTrace();
        }

    }
}
