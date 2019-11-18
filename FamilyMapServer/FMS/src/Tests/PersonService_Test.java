package Tests;

import Dao.DataAccessException;
import Model.User_Model;
import Requests.Login_Request;
import Results.Login_Results;
import Results.Person_Results;
import Results.Register_Results;
import Services.Clear_Service;
import Services.Login_Service;
import Services.Person_Service;
import Services.Register_Service;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;

public class PersonService_Test {
    @Test
    public void PersonPass(){
        Register_Service registerService = new Register_Service();
        try {
            Clear_Service clearService = new Clear_Service();
            clearService.clear();

            Register_Results registerResults = registerService.registerService(new User_Model(null, "username", "password", "email", "firstName", "lastName", "m"));
            String authToken = registerResults.getAuthorizationToken();

            Person_Service personService = new Person_Service();
            Person_Results personResults = personService.getPersonsByToken(authToken);

            Gson gson = new Gson();
            String json = gson.toJson(personResults);
            Assertions.assertNotNull(json);
            Assertions.assertEquals(31, personResults.getData().length);

        } catch (SQLException | DataAccessException e) {
            Assertions.fail();
            e.printStackTrace();
        }


    }
    @Test
    public void PersonFail(){
        Register_Service registerService = new Register_Service();
        try {
            Clear_Service clearService = new Clear_Service();
            clearService.clear();

            Register_Results registerResults = registerService.registerService(new User_Model(null, "username", "password", "email", "firstName", "lastName", "m"));
            String authToken = registerResults.getAuthorizationToken()+"a";

            Person_Service personService = new Person_Service();
            Person_Results personResults = personService.getPersonsByToken(authToken);

            Gson gson = new Gson();
            String json = gson.toJson(personResults);
            Assertions.assertNotNull(json);
            Assertions.assertEquals("{\"message\":\"Error not a valid token\"}", json);

        } catch (SQLException | DataAccessException e) {
            Assertions.fail();
            e.printStackTrace();
        }

    }
}
