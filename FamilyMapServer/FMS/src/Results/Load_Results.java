package Results;

import Model.Event_Model;
import Model.Person_Model;
import Model.User_Model;

/**
 * holds load results
 */
public class Load_Results {
    private String message;

    public Load_Results(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
