import Handlers.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class server {
    private static final int MAX_CONNECTIONS = 10;
    private static int PORT = 8080;
    private static HttpServer server;

    public static void main(String[] args){
        if(args.length > 0){
            PORT = Integer.parseInt(args[0]);
        }
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), MAX_CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        server.createContext("/user/register", new Register_Handler());
        server.createContext("/user/login", new Login_Handler());
        server.createContext("/clear", new Clear_Handler());
        server.createContext("/fill", new Fill_Handler());
        server.createContext("/load", new Load_Handler());
        server.createContext("/person", new Person_Handler());
        server.createContext("/event", new Event_Handler());
        server.createContext("/", new Default_Handler());

        server.start();
        System.out.println("Server is working on port " + PORT);
    }


}
