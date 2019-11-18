package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.util.Scanner;

public class Default_Handler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {

        try {
            URI uri = httpExchange.getRequestURI();
            String path = uri.getPath();
            System.out.println(uri);
            System.out.println(path);

            //sendBack.write("<html><body>hello</body></html>");
            String filePath = "web";
            if(uri.toString().equals("/") || uri.toString().length() == 0){
                 filePath = filePath + "/index.html";
            } else {
                filePath = filePath + uri.toString();
            }
            File file = new File(filePath);
            if(file.exists()){
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream sendBack = httpExchange.getResponseBody();
                Files.copy(file.toPath(), sendBack);
                sendBack.close();
            } else {
                File file1 = new File("web/HTML/404.html");
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream sendBack = httpExchange.getResponseBody();
                Files.copy(file1.toPath(), sendBack);
                sendBack.close();
            }



        } catch (IOException e){
            String filePath = "web/HTML/404.html";
            File file = new File(filePath);
            OutputStream sendBack = httpExchange.getResponseBody();
            Files.copy(file.toPath(), sendBack);
//            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            httpExchange.close();
            //e.printStackTrace();
        }
    }
}
