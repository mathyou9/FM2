package Services;

import Dao.AuthorizationToken_DAO;
import Dao.DataAccessException;
import Dao.Database;
import Dao.User_DAO;
import Model.AuthorizationToken_Model;
import Model.User_Model;
import Requests.Login_Request;
import Results.Login_Results;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

/**
 * service that takes care of login requests
 */
public class Login_Service {

    private String dbName = "database.db";
    private String connectionURL = "jdbc:sqlite:" + dbName;

    public Login_Results DoService(Login_Request lR) {
        User_DAO userDao = new User_DAO();
        User_Model user = new User_Model(lR);
        AuthorizationToken_Model authToken = new AuthorizationToken_Model();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            authToken.setAuthorizationToken(UUID.randomUUID().toString().substring(0,8));
            authToken.setUserName(user.getUserName());
            AuthorizationToken_DAO authDao = new AuthorizationToken_DAO();
            authDao.createToken(connection, authToken);
            user = userDao.find(connection, user.getUserName());

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(user == null){
            return new Login_Results("error: No user with that login");
        }
        if(user.getPassword().equals(lR.getPassword())){
            return new Login_Results(user.getUserName(), user.getPersonID(), authToken.getAuthorizationToken());
        } else{
            return new Login_Results("error: wrong password or username");
        }

    }
}
