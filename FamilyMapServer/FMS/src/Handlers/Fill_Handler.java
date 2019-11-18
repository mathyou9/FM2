package Handlers;

import Results.Fill_Results;
import Services.Fill_Service;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;

public class Fill_Handler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        URI uri = httpExchange.getRequestURI();
        String path = new StringBuilder(uri.toString()).deleteCharAt(0).toString();
        System.out.println(path);
        String[] fillOptions = path.split("/");
        for (String option: fillOptions) {
            System.out.println(option);
        }
        System.out.println(fillOptions[1]);

        Fill_Service fillService = new Fill_Service();
        Fill_Results fillResults = null;
        if(fillOptions.length < 3){
            fillResults= fillService.fillResults(fillOptions[1], 4);
        } else {
            fillResults = fillService.fillResults(fillOptions[1], Integer.parseInt(fillOptions[2]));
        }
        Gson gson = new Gson();

        String json = gson.toJson(fillResults);
        OutputStream response = httpExchange.getResponseBody();
        OutputStreamWriter sw = new OutputStreamWriter(response, "UTF-8");
        sw.write(json);
        sw.flush();
        response.close();

        System.out.println("Register Done");
    }
}
