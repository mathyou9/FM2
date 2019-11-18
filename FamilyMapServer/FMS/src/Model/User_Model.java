package Model;

import Requests.Login_Request;

/**
 * User Model
 */
public class User_Model {

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    public User_Model(String pID, String uN, String p, String e, String fN, String lN, String g ){
        userName = uN;
        password = p;
        email = e;
        firstName = fN;
        lastName = lN;
        gender = g;
        personID = pID;
    }
    public User_Model(String uN, String fN, String lN){
        userName = uN;
        firstName = fN;
        lastName = lN;
        password = "";
        email = "";
        gender = "";
        personID = "";
    }
    public User_Model(Login_Request lR){
        userName = lR.getUserName();
        password = lR.getPassword();
    }

    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getGender(){
        return gender;
    }
    public String getPersonID(){
        return personID;
    }
    public void setUserName(String username){
        this.userName = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public void setPersonID(String personID){
        this.personID = personID;
    }

}
