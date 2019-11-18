package Handlers;

import Results.PersonID_Results;
import Results.Person_Results;
import Services.Person_Service;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;

public class Person_Handler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String authToken = httpExchange.getRequestHeaders().getFirst("Authorization");
        System.out.println(authToken);
        String path = new StringBuilder(uri.toString()).deleteCharAt(0).toString();
        String[] fillOptions = path.split("/");
        for(String option : fillOptions){
            System.out.println(option);
        }
        Person_Results eventResults = null;
        PersonID_Results eventIDResults = null;
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        Gson gson = new Gson();
        String json = null;
        if(fillOptions.length > 1){
            Person_Service eventService = new Person_Service();
            eventIDResults = eventService.getPersonByPersonID(fillOptions[1], authToken);
            json = gson.toJson(eventIDResults);
        } else {
            Person_Service eventService = new Person_Service();
            eventResults = eventService.getPersonsByToken(authToken);
            json = gson.toJson(eventResults);
        }

        OutputStream response = httpExchange.getResponseBody();
        OutputStreamWriter sw = new OutputStreamWriter(response);
        sw.write(json);
        sw.flush();
        response.close();
    }
}
