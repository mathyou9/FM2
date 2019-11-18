package Handlers;


import Dao.Database;
import Model.User_Model;
import Results.Clear_Results;
import Services.Clear_Service;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.sql.SQLException;

public class Clear_Handler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        Clear_Service clearService = new Clear_Service();
        Clear_Results clearResults = clearService.clear();

        Gson gson = new Gson();

        String json = gson.toJson(clearResults);
        OutputStream response = httpExchange.getResponseBody();
        OutputStreamWriter sw = new OutputStreamWriter(response);
        sw.write(json);
        sw.flush();
        response.close();
        System.out.println("Clear Done");
    }
}
