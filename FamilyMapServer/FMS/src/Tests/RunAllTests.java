package Tests;

import Dao.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({ClearService_Test.class, EventService_Test.class, FillService_Test.class, LoadService_Test.class, LoginService_Test.class, PersonService_Test.class, RegisterService_Test.class, UserDAO_Test.class, PersonDAO_Test.class, EventDAO_Test.class, AuthDAO_Test.class})
public class RunAllTests {


}
