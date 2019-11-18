package Results;

/**
 * holds login result data
 */
public class Login_Results {
    private String authToken;
    private String userName;
    private String personID;
    private String message;

    public Login_Results(String uN, String pID, String aT){
        this.userName = uN;
        this.personID = pID;
        this.authToken = aT;
    }
    public Login_Results(String eM){
        this.message = eM;
    }
    public Login_Results(){

    }

    public String getToken(){
        return authToken;
    }
    public void setToken(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
