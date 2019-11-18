package Handlers;

import Requests.Login_Request;
import Results.Login_Results;
import Services.Login_Service;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Base64;

public class Login_Handler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        Reader reader = new InputStreamReader(httpExchange.getRequestBody());
        Gson gson = new Gson();

        Login_Request loginRequest = gson.fromJson(reader, Login_Request.class);
        Login_Results loginResults = new Login_Service().DoService(loginRequest);
        String json = gson.toJson(loginResults).toString();
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStream response = httpExchange.getResponseBody();
        OutputStreamWriter sw = new OutputStreamWriter(response);
        sw.write(json);
        sw.flush();
        response.close();
        System.out.println("Login Done");
    }
}
