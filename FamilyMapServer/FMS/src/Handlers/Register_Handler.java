package Handlers;

import Dao.DataAccessException;
import Model.AuthorizationToken_Model;
import Model.User_Model;
import Requests.Login_Request;
import Requests.Register_Request;
import Results.Login_Results;
import Results.Register_Results;
import Services.Register_Service;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.sql.SQLException;

public class Register_Handler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {


        Reader reader = new InputStreamReader(httpExchange.getRequestBody());

        Gson gson = new Gson();
        User_Model user = gson.fromJson(reader, User_Model.class);

        Register_Request registerRequest = new Register_Request();
        registerRequest.setUser(user);



        Register_Service registerService = new Register_Service();
        Register_Results registerResults = null;
        String json = null;
        try {
            registerResults = registerService.registerService(user);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            json = gson.toJson(registerResults);
        } catch (SQLException | DataAccessException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            json = "{'message':'" + e.getMessage() + "'}";
        }

//        String json = "{json}";
        OutputStream response = httpExchange.getResponseBody();
        OutputStreamWriter sw = new OutputStreamWriter(response);
        sw.write(json);
        sw.flush();
        response.close();
        System.out.println("Register Done");
    }
}
