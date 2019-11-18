package Handlers;

import Requests.Load_Request;
import Requests.Login_Request;
import Results.Load_Results;
import Results.Login_Results;
import Services.Load_Service;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;

public class Load_Handler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        Reader reader = new InputStreamReader(httpExchange.getRequestBody());
        Gson gson = new Gson();
        Load_Request loadRequest = null;
        String json = null;
        try{
            loadRequest = gson.fromJson(reader, Load_Request.class);
            Load_Service loadService = new Load_Service();
            Load_Results loadResults = loadService.loadResults(loadRequest);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            json = gson.toJson(loadResults);
        } catch (JsonSyntaxException e){
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            json = "{\"error\":\"" + e.getMessage() + "\"}";
        }
        OutputStream response = httpExchange.getResponseBody();
        OutputStreamWriter sw = new OutputStreamWriter(response);
        sw.write(json);
        sw.flush();
        response.close();
        System.out.println("Load Done");


    }
}
