package Tests;

import Dao.DataAccessException;
import Model.User_Model;
import Results.*;
import Services.*;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class FillService_Test {
    @Test
    public void FillPass(){
        Register_Service registerService = new Register_Service();
        try {
            Clear_Service clearService = new Clear_Service();
            clearService.clear();

            Register_Results registerResults = registerService.registerService(new User_Model(null, "username", "password", "email", "firstName", "lastName", "m"));
            String authToken = registerResults.getAuthorizationToken();

            Fill_Service fillService = new Fill_Service();
            Fill_Results fillResults = fillService.fillResults("username", 4);

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
    public void FillFail(){
        try{


        Register_Service registerService = new Register_Service();
        Clear_Service clearService = new Clear_Service();
        clearService.clear();

        Fill_Service fillService = new Fill_Service();
        Fill_Results fillResults = fillService.fillResults("username", 4);


        Gson gson = new Gson();
        String json = gson.toJson(fillResults);
        Assertions.assertNotNull(json);
        Assertions.assertEquals("{\"message\":\"Error no user\"}", json);
        } catch (Throwable e){
            Assertions.fail();
            e.printStackTrace();
        }
    }
}
