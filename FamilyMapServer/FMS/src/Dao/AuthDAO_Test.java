package Dao;

import Model.AuthorizationToken_Model;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AuthDAO_Test {

    private AuthorizationToken_DAO auth = new AuthorizationToken_DAO();
    Connection connection = null;
    String dbName = "database.db";
    String connectionURL = "jdbc:sqlite:" + dbName;

    @Test
    public void createPass(){
        AuthorizationToken_Model authTokenModel = null;
        AuthorizationToken_Model thisAuth = new AuthorizationToken_Model("authToken", "username");
        String authString = null;
        String username = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            auth.deleteAllTokens(connection);
            auth.createToken(connection, thisAuth);
            username = auth.getUser(connection, "authToken");

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(thisAuth.getUserName(), username);

    }
    @Test
    public void createFail(){
        AuthorizationToken_Model authTokenModel = null;
        AuthorizationToken_Model thisAuth = new AuthorizationToken_Model("authToken", "username");
        String authString = null;
        String username = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            auth.deleteAllTokens(connection);
            auth.createToken(connection, thisAuth);
            auth.createToken(connection, thisAuth);
            username = auth.getUser(connection, "authToken");

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertNull(username);

    }
    @Test
    public void getUserPass(){
        AuthorizationToken_Model authTokenModel = null;
        AuthorizationToken_Model thisAuth = new AuthorizationToken_Model("authToken", "username");
        String authString = null;
        String username = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            auth.deleteAllTokens(connection);
            auth.createToken(connection, thisAuth);
            username = auth.getUser(connection, "authToken");

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(thisAuth.getUserName(), username);
    }
    @Test
    public void getUserFail(){
        AuthorizationToken_Model authTokenModel = null;
        AuthorizationToken_Model thisAuth = new AuthorizationToken_Model("authToken", "username");
        String authString = null;
        String username = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            auth.deleteAllTokens(connection);
            auth.createToken(connection, thisAuth);
            auth.createToken(connection, thisAuth);
            username = auth.getUser(connection, "authToken");

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertNull(username);
    }
    @Test
    public void deleteAllTokensPass(){
        AuthorizationToken_Model authTokenModel = null;
        AuthorizationToken_Model thisAuth = new AuthorizationToken_Model("authToken", "username");
        String authString = null;
        String username = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            auth.deleteAllTokens(connection);
            auth.createToken(connection, thisAuth);
            auth.deleteAllTokens(connection);
            username = auth.getUser(connection, "authToken");

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertNull(username);
    }
    @Test
    public void deleteAllTokensPass2(){
        AuthorizationToken_Model authTokenModel = null;
        AuthorizationToken_Model thisAuth = new AuthorizationToken_Model("authToken", "username");
        String authString = null;
        String username = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            auth.deleteAllTokens(connection);
            auth.createToken(connection, thisAuth);
            auth.deleteAllTokens(connection);
            auth.createToken(connection, thisAuth);
            auth.deleteAllTokens(connection);
            auth.createToken(connection, thisAuth);
            auth.deleteAllTokens(connection);
            auth.createToken(connection, thisAuth);
            auth.deleteAllTokens(connection);
            username = auth.getUser(connection, "authToken");

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertNull(username);
    }
}
