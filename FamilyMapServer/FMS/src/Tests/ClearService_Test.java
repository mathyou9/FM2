package Tests;

import Dao.DataAccessException;
import Dao.Person_DAO;
import Model.User_Model;
import Requests.Login_Request;
import Results.Clear_Results;
import Results.Login_Results;
import Services.Clear_Service;
import Services.Login_Service;
import Services.Register_Service;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;

public class ClearService_Test {

    @Test
    public void clearPass(){
        Register_Service registerService = new Register_Service();
        try {
            registerService.registerService(new User_Model(null, "username", "password", "email", "firstName", "lastName", "m"));
            Clear_Service clearService = new Clear_Service();
            Clear_Results clearResults = clearService.clear();
            Gson gson = new Gson();
            String json = gson.toJson(clearResults);
            Assertions.assertEquals("{\"message\":\"Clear Succeeded.\"}", json);
        } catch (SQLException | DataAccessException e) {
            Assertions.fail();
            e.printStackTrace();
        }


    }
    @Test
    public void clearFail(){
        Register_Service registerService = new Register_Service();
        Login_Service loginService = new Login_Service();
        try {
            User_Model userModel = new User_Model(null, "username", "password", "email", "firstName", "lastName", "m");
            registerService.registerService(userModel);
            Clear_Service clearService = new Clear_Service();
            Clear_Results clearResults = clearService.clear();
            Gson gson = new Gson();
            Login_Request loginRequest = new Login_Request();
            loginRequest.setUserName(userModel.getUserName());
            loginRequest.setPassword(userModel.getPassword());
            Login_Results loginResults = loginService.DoService(loginRequest);
            String json = gson.toJson(loginResults);
            Assertions.assertEquals("{\"message\":\"error: No user with that login\"}", json);

        } catch (SQLException | DataAccessException e) {
            Assertions.fail();
            e.printStackTrace();
        }
    }
}
