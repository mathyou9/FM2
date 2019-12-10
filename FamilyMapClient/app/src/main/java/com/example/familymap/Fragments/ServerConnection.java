package com.example.familymap.Fragments;

import android.os.AsyncTask;

import com.example.familymap.Models.DataCache;
import com.example.familymap.Models.Event_Model;
import com.example.familymap.Models.Person_Model;
import com.example.familymap.Models.User_Model;
import com.example.familymap.Requests.Login_Request;
import com.example.familymap.Results.Event_Results;
import com.example.familymap.Results.Person_Results;
import com.example.familymap.Results.Register_Results;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerConnection extends AsyncTask<URL, Integer, Long> {
    private static String host;
    private static int port;
    private static ServerConnection serverConnection;
    private static String base;
    private Login_Request loginResults;



    public ServerConnection(){

    }
    public static ServerConnection getConnection(){
        if(serverConnection == null){
            serverConnection = new ServerConnection();
        }
        return serverConnection;
    }

    @Override
    protected Long doInBackground(URL... urls) {
        return null;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }


    public static Object[] Login(User_Model user){
        DataCache dataCache = DataCache.getInstance();
        base = "http://" + host + ":" + port;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(base + "/user/login");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Accept", "application/json");
            Gson gson = new Gson();
            String request = gson.toJson(user);
            OutputStream body = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(body);
            writer.write(request);
            writer.flush();
            writer.close();
            body.close();
            connection.connect();
            if(connection.getResponseCode() == 200){
                InputStream response = connection.getInputStream();
                StringBuilder sb = new StringBuilder();
                InputStreamReader reader = new InputStreamReader(response);
                char[] buffer = new char[1024];
                int length;
                while((length = reader.read(buffer)) > 0) {
                    sb.append(buffer, 0, length);
                }
                response.close();
                Register_Results result = gson.fromJson(sb.toString(), Register_Results.class);
                if(result.getAuthorizationToken() == null){
                    return new Object[]{false, result.getMessage()};
                }
                if(getData(result.getAuthorizationToken(), result.getPersonID())){
                    Person_Model person = dataCache.getPerson(result.getPersonID());
                    return new Object[]{true, "Welcome back " + person.getFirstName() + " " + person.getLastName() + "!"};
                }
            } else {
                return new Object[] {false, "Couldn't login"};
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return new Object[] {false, "Couldn't login"};

    }



    public static Object[] Register(User_Model user){
        DataCache dataCache = DataCache.getInstance();
        base = "http://" + host + ":" + port;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(base + "/user/register");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Accept", "application/json");
            Gson gson = new Gson();
            String request = gson.toJson(user);
            OutputStream body = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(body);
            writer.write(request);
            writer.flush();
            writer.close();
            body.close();
            connection.connect();
            if(connection.getResponseCode() == 200){
                InputStream response = connection.getInputStream();
                StringBuilder sb = new StringBuilder();
                InputStreamReader reader = new InputStreamReader(response);
                char[] buffer = new char[1024];
                int length;
                while((length = reader.read(buffer)) > 0) {
                    sb.append(buffer, 0, length);
                }
                response.close();
                Register_Results result = gson.fromJson(sb.toString(), Register_Results.class);
                if(result.getAuthorizationToken() == null){
                    return new Object[]{false, result.getMessage()};
                }
                if(getData(result.getAuthorizationToken(), result.getPersonID())){
                    Person_Model person = dataCache.getPerson(result.getPersonID());
                    return new Object[]{true, "Welcome " + person.getFirstName() + " " + person.getLastName() + "!"};
                }
            } else {
                return new Object[] {false, "Couldn't register"};
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return new Object[] {false, "Couldn't register"};
    }
    public static boolean getData(String token, String pID){
        Person_Model[] persons;
        Event_Model[] events;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(base + "/person");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(false);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Accept", "application/json");
            connection.addRequestProperty("Authorization", token);
            connection.connect();
            if(connection.getResponseCode() == 200) {
                InputStream response = connection.getInputStream();
                StringBuilder sb = new StringBuilder();
                InputStreamReader reader = new InputStreamReader(response);
                char[] buffer = new char[1024];
                int length;
                while ((length = reader.read(buffer)) > 0) {
                    sb.append(buffer, 0, length);
                }
                response.close();
                Gson gson = new Gson();
                Person_Results result = gson.fromJson(sb.toString(), Person_Results.class);
                persons = result.getData();
            } else {
                return false;
            }
            connection.disconnect();
            url = new URL(base + "/event");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(false);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Accept", "application/json");
            connection.addRequestProperty("Authorization", token);
            connection.connect();
            if(connection.getResponseCode() == 200) {
                InputStream response = connection.getInputStream();
                StringBuilder sb = new StringBuilder();
                InputStreamReader reader = new InputStreamReader(response);
                char[] buffer = new char[1024];
                int length;
                while ((length = reader.read(buffer)) > 0) {
                    sb.append(buffer, 0, length);
                }
                response.close();
                Gson gson = new Gson();
                Event_Results result = gson.fromJson(sb.toString(), Event_Results.class);
                events = result.getData();
            } else {
                return false;
            }
            DataCache dataCache = DataCache.getInstance();
            dataCache.SetData(host, port, persons, events);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return false;
    }

}
