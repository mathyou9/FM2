package Dao;

import Model.User_Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User_DAO {

    public boolean insert(Connection connection, User_Model user) throws DataAccessException {

        PreparedStatement stmt = null;
        String sql = "insert into Users (PersonID, UserName, FirstName, LastName, Password, Email, Gender) values (?, ?, ?, ?, ?, ?, ?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getPersonID());
            stmt.setString(2, user.getUserName());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getEmail());
            stmt.setString(7, user.getGender());
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
    public User_Model find(Connection connection, String ID){
        User_Model thisUser = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT PersonID, UserName, Password, Email, FirstName, LastName, Gender FROM Users WHERE UserName = '" + ID + "'";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()){
                thisUser = new User_Model(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thisUser;
    }
    public boolean remove(Connection connection, String ID){
        User_Model thisUser = null;
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM Users WHERE PersonID = '" + ID + "'";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteAllUsers(Connection connection){
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM Users";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
