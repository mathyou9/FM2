package Handlers;

import Requests.Fill_Request;
import Results.*;
import Services.Clear_Service;
import Services.Event_Service;
import Services.Fill_Service;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.sql.SQLException;
import java.util.Scanner;

public class Event_Handler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String authToken = httpExchange.getRequestHeaders().getFirst("Authorization");
        System.out.println(authToken);
        String path = new StringBuilder(uri.toString()).deleteCharAt(0).toString();
        String[] fillOptions = path.split("/");
        for(String option : fillOptions){
            System.out.println(option);
        }
        Event_Results eventResults = null;
        EventID_Results eventIDResults = null;

        Gson gson = new Gson();
        String json = null;
        if(fillOptions.length > 1){
            Event_Service eventService = new Event_Service();
            try {
                eventIDResults = eventService.getEventByEventID(fillOptions[1], authToken);
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                json = gson.toJson(eventIDResults);
            } catch (SQLException e) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                json = "{\"message\":\"Error" + e.getMessage() + "\"}";
                //e.printStackTrace();
            }
        } else {
            Event_Service eventService = new Event_Service();
            try {
                eventResults = eventService.getEventByToken(authToken);

                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                json = gson.toJson(eventResults);
            } catch (SQLException e) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                json = "{\"message\":\"Error" + e.getMessage() + "\"}";
                //e.printStackTrace();
            }

        }
        OutputStream response = httpExchange.getResponseBody();
        OutputStreamWriter sw = new OutputStreamWriter(response);
        sw.write(json);
        sw.flush();
        response.close();
    }
}
