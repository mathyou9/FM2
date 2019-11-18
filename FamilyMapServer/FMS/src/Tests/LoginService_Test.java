package Tests;

import Dao.DataAccessException;
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

import java.sql.SQLException;

public class LoginService_Test {

    @Test
    public void LoginPass(){
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
    public void LoginFail(){
        Register_Service registerService = new Register_Service();
            Clear_Service clearService = new Clear_Service();
            clearService.clear();

            Login_Request loginRequest = new Login_Request();
            loginRequest.setUserName("username");
            loginRequest.setPassword("password");
            Login_Service loginService = new Login_Service();
            Login_Results loginResults = loginService.DoService(loginRequest);

            Gson gson = new Gson();
            String json = gson.toJson(loginResults);
            Assertions.assertNull(loginResults.getUserName());
            Assertions.assertNull(loginResults.getToken());
            Assertions.assertNull(loginResults.getPersonID());
            Assertions.assertEquals("{\"message\":\"error: No user with that login\"}", json);

    }
}
