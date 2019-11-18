package com.example.familymap.Fragments;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.familymap.Models.User_Model;
import com.example.familymap.Requests.Login_Request;
import com.example.familymap.Results.Login_Results;
import com.example.familymap.Results.Register_Results;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

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


    public Object[] Login(User_Model user){
        base = "http://" + host + ":" + port;
        JSONObject login = new JSONObject();
        try {
            login.put("username", user.getUserName());
            login.put("password", user.getPassword());
            JSONObject response = GetUser(login);
            System.out.println(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject GetUser(JSONObject login){
        String uri = "/user/login";
        try {
            URL url = new URL(base + uri);
            System.out.println(url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Accept", "application/json");

            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return login;
    }

    public static Object[] Register(User_Model user){
        base = "http://" + host + ":" + port;
        try {
            URL url = new URL(base + "/user/register");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
            return new Object[]{true, "welcome"};
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Object[] {false, "Couldnt register"};
    }

}
