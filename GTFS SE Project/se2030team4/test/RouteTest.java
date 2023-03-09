import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @Author for all, Joe Kluding;
 */
public class RouteTest {

    FileIO fileIO;

    @BeforeEach
    void setUp(){
        fileIO = new FileIO();

    }

    @Test
    public void testInvalidHeader(){
        Assertions.assertFalse(fileIO.validateRoutesHeader("retpoiuda"));
    }
    @Test
    public void testValidHeader(){
        Assertions.assertTrue(fileIO.validateRoutesHeader("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color"));
    }
    @Test
    public void testInvalidFile(){
        String invalid1 = "30X七七,MCTS,30X,Sherman - WisconsinEXpress,,3,七七七七七七七,008345,";
        String invalid2 = "1387,OAKLAND & CUMBERLAND #1387,,43.1015111,-87.8875303,";
        ArrayList<String> invalidArray = new ArrayList<>();
        invalidArray.add(null);
        invalidArray.add(invalid1);
        Assertions.assertFalse(fileIO.routesValidate(invalidArray));
        invalidArray.clear();
        invalidArray.add(null);
        invalidArray.add(null);
        Assertions.assertFalse(fileIO.routesValidate(invalidArray));
        invalidArray.clear();
        invalidArray.add(null);
        invalidArray.add(invalid2);
        Assertions.assertFalse(fileIO.routesValidate(invalidArray));
    }
    @Test
    public void testValidFile(){
        String valid1 = "Route1,MTU,1,South Ave,\"The Shelby Mall bus operates between downtown and Shelby Mall with service to Gundersen Lutheran Medical Center.  The route operates M-F 5:10 a.m. - 10:40 p.m., Sat. 7:40 a.m. - 7:40 p.m., Sun 7:40 a.m. - 6:40 p.m.  \",3,,83C0E8,";
        String valid2 = "219,MCTS,219,Oak Creek Shuttle,,3,,008345,";
        ArrayList<String> validArray = new ArrayList<>();
        validArray.add(null);
        validArray.add(valid1);
        Assertions.assertTrue(fileIO.routesValidate(validArray));
        validArray.clear();
        validArray.add(null);
        validArray.add(valid2);
        Assertions.assertTrue(fileIO.routesValidate(validArray));
    }
}
