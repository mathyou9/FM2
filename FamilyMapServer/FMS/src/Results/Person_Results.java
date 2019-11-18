package Results;

import Model.Person_Model;

/**
 * person result information
 */
public class Person_Results {
    private Person_Model[] data;
    private String message;

    public Person_Results(String message){
        this.message = message;
    }

    public Person_Results(Person_Model[] data) {
        this.data = data;
    }

    public Person_Model[] getData() {
        return data;
    }

    public void setData(Person_Model[] data) {
        this.data = data;
    }
}
