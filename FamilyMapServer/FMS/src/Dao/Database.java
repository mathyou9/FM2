package Dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private AuthorizationToken_DAO authDao;
    private Event_DAO eventDao;
    private Person_DAO personDao;
    private User_DAO userDao;
    private Connection connection;

    public Database(){
        authDao = new AuthorizationToken_DAO();
        eventDao = new Event_DAO();
        personDao = new Person_DAO();
        userDao = new User_DAO();
    }

    public void open() throws SQLException {
        String dbName = "database.db";
        String connectionURL = "jdbc:sqlite:" + dbName;
        connection = DriverManager.getConnection(connectionURL);
        connection.setAutoCommit(false);
    }
    public void reset(){
        personDao.deleteAllPersons(connection);
        eventDao.deleteAllEvents(connection);
        userDao.deleteAllUsers(connection);
        authDao.deleteAllTokens(connection);
    }
    public void close() throws SQLException {
        connection.commit();
        connection.close();
        connection = null;
    }
    public Connection getConnection(){
        return connection;
    }

    public Event_DAO getEventDao() {
        return eventDao;
    }

    public void setEventDao(Event_DAO eventDao) {
        this.eventDao = eventDao;
    }

    public Person_DAO getPersonDao() {
        return personDao;
    }

    public void setPersonDao(Person_DAO personDao) {
        this.personDao = personDao;
    }

    public User_DAO getUserDao() {
        return userDao;
    }

    public void setUserDao(User_DAO userDao) {
        this.userDao = userDao;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
