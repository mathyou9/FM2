package Requests;

import Model.User_Model;

/**
 * this handles requests for registering users
 */

public class Register_Request {
    private User_Model user;

    public String getUserName(){
        return user.getUserName();
    }
    public String getPassword(){
        return user.getPassword();
    }
    public String getEmail(){
        return user.getEmail();
    }
    public String getFirstName(){
        return user.getFirstName();
    }
    public String getLastName(){
        return user.getLastName();
    }
    public String getGender(){
        return user.getEmail();
    }
    public User_Model getUser() {return user;}
    public void setUser(User_Model user){
        this.user = user;
    }
    public void setUserName(String userName){
        user.setUserName(userName);
    }
    public void setPassword(String password){
        user.setPassword(password);
    }
    public void setEmail(String email){
        user.setEmail(email);
    }
    public void setFirstName(String firstName){
        user.setFirstName(firstName);
    }
    public void setLastName(String lastName){
        user.setLastName(lastName);
    }
    public void setGender(String gender){
        user.setGender(gender);
    }

}
