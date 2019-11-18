package Dao;

import Model.AuthorizationToken_Model;
import Model.User_Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles Authorization Tokens
 */
public class AuthorizationToken_DAO {

    private String authToken;
    private String userName;

    public boolean createToken(Connection connection, AuthorizationToken_Model token) throws DataAccessException {
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT into AuthorizationTokens (AuthorizationToken, UserName) values (?,?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, token.getAuthorizationToken());
            stmt.setString(2, token.getUserName());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting into the database");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void deleteToken(Connection connection, AuthorizationToken_Model token){
        return;
    }
    public String getUser(Connection connection, String token){
        AuthorizationToken_Model thisEvent = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM AuthorizationTokens WHERE AuthorizationToken = '" + token + "'";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()){
                thisEvent = new AuthorizationToken_Model(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if(thisEvent == null){
                return null;
            }
            return thisEvent.getUserName();
        }
    }
    public String getToken(Connection connection, String userName){
        AuthorizationToken_Model thisEvent = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM AuthorizationTokens WHERE userName = '" + userName + "'";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()){
                thisEvent = new AuthorizationToken_Model(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if(thisEvent == null){
                return null;
            }
            return thisEvent.getUserName();
        }
    }

    public boolean deleteAllTokens(Connection connection){
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM AuthorizationTokens";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
