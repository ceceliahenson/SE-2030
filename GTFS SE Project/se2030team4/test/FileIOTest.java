import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {
    GTFSSubject gtfs;
    Trip trip;
    FileIO fileIO;
    Stop stop;

    @BeforeEach
    void setUp() {
        gtfs = new GTFSSubject(Subject.observers);
        fileIO = new FileIO();
        trip = new Trip(null, null, null, null, 0, 0, null);
        stop = new Stop(null,null,null,0.0, 0.0);
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * @author Ethan Jeffers
     */
   @Test
    public void testInvalidTripImport() {

        // Make sure it does not allow if there is not the required elements
        Assertions.assertNull(fileIO.validateTripImport(
                ",17-SEP_SUN,21736564_2535,60TH-VLIET,0,64102,17-SEP_64_0_23"));
        Assertions.assertNull(fileIO.validateTripImport(
                "64,17-SEP_SUN,,60TH-VLIET,0,64102,17-SEP_64_0_23"));
        Assertions.assertNull(fileIO.validateTripImport(
                "64,17-SEP_SUN,21736564_2535,60TH-VLIET,0,64102,"));

        // Make sure that a string cannot be passed into a place of an integer
        Assertions.assertNull(fileIO.validateTripImport(
                "64,17-SEP_SUN,21736564_2535,60TH-VLIET,a,64102,17-SEP_64_0_23"));
        Assertions.assertNull(fileIO.validateTripImport(
                "64,17-SEP_SUN,21736564_2535,60TH-VLIET,0,sdfsdf,17-SEP_64_0_23"));

        //Tests that it will not allow an invalid commas without quotes surrounding
        Assertions.assertNull(fileIO.validateTripImport(
                "64,17-SEP_SUN,21736564_2535,,60TH-VLIET,0,64102,17-SEP_64_0_23"));
        Assertions.assertNull(fileIO.validateTripImport(
                "6417-SEP_SUN,21736564_2535,60TH-VLIET,0,64102,17-SEP_64_0_23"));

        // Quotes in invalid locations
        Assertions.assertNull(fileIO.validateTripImport(
                "\"64\",17-SEP_SUN,21736564_2535,\"60TH-,VLIET\",0,64102,17-SEP_64_0_23"));
    }

    /**
     * Makes sure that correct lines pass
     *
     * @author Ethan Jeffers
     */
   @Test
    public void testValidTripImports() {
        Assertions.assertNotNull(fileIO.validateTripImport(
                "64,17-SEP_SUN,21736564_2535,\"60TH-,VLIET\",0,64102,17-SEP_64_0_23"));
        Assertions.assertNotNull(fileIO.validateTripImport(
                "64,17-SEP_SUN,21736564_2535,60TH-VLIET,0,64102,17-SEP_64_0_23"));
    }

    /**
     * author: cece
     * tests the invalid imports of a stop file
     */

    @Test
    public void testInvalidStop() {
        // tests missing element
        assertFalse(fileIO.validateStopImport("STATE & 5101 #6712,,43.0444475,-87.9779369"));
        assertFalse(fileIO.validateStopImport("6712,STATE & 5101 #6712,,43.0444475,"));
        // tests invalid lat
        assertFalse(fileIO.validateStopImport("6712,STATE & 5101 #6712,,92,-87.9779369"));

        // tests invalid stop id as string
        assertFalse(fileIO.validateStopImport("hi,STATE & 5101 #6712,,43.0444475,-87.9779369"));

        //tests if lowercase string in double lat spot
        assertFalse(fileIO.validateStopImport("6712,STATE & 5101 #6712,,hi,-87.9779369"));
        // tests if lowercase string in double lon spot
        assertFalse(fileIO.validateStopImport("6712,STATE & 5101 #6712,,43.0444475,hi"));

        // test if both lon and lat are lowercase string
        assertFalse(fileIO.validateStopImport("6712,STATE & 5101 #6712,,hi,hi"));

        // tests if empty string
        assertFalse(fileIO.validateStopImport(""));

        //tests if uppercase string in double lat spot
        assertFalse(fileIO.validateStopImport("6712,STATE & 5101 #6712,,HI,-87.9779369"));
        // tests if uppercase string in double lon spot
        assertFalse(fileIO.validateStopImport("6712,STATE & 5101 #6712,,43.0444475,HI"));

        // test if both lon and lat are uppercase string w number
        assertFalse(fileIO.validateStopImport("6712,STATE & 5101 #6712,,HI22,HI22"));

    }

    /**
     * author: cece
     * tests the valid imports of stop files
     *
     */
    @Test
    public void testValidStops(){
        // tests regular stop format
        assertTrue(fileIO.validateStopImport("6712,STATE & 5101 #6712,,43.0444475,-87.9779369"));
        assertTrue(fileIO.validateStopImport("1250,HOLTON & CHAMBERS #1250,,43.072752,-87.905143"));
        // testing with a stop name with a quotes
        assertTrue(fileIO.validateStopImport("6712,\"STATE & 5101 #6712\",,43.0444475,-87.9779369"));
        //testing stop description with quotes
        assertTrue(fileIO.validateStopImport("6712,STATE & 5101 #6712,\" \",43.0444475,-87.9779369"));
        // testing stop name and stop description with quotes
        assertTrue(fileIO.validateStopImport("45D87ED9BC,\"E. Hamilton and Cypress\",\"At the Crosswalk.\",44.783940,-91.449030"));
        assertTrue(fileIO.validateStopImport("3DE63946FD,\"Graham and Emery\",\"Near cross walk at YMCA\",44.806800,-91.496800"));
        // testing stop description with comma in it
        assertTrue(fileIO.validateStopImport("0995B4DB83,\"State and Lincoln\",\"Even side of street, near cross walk\",44.799800,-91.495030"));
    }

    /**
     * author: cece
     * this test check the stop method response to a valid and not valid input
     */
    @Test
    void testStopMethods(){
       Stop stop = new Stop("6712","STATE & 5101 #6712","",
                43.0444475,-87.9779369);

       // testing aspects of the stop files to make sure the methods return the correct value
        assertEquals("6712",stop.getStop_ID());
        assertNotEquals(6712,stop.getStop_ID());

        assertEquals("STATE & 5101 #6712",stop.getStop_name());

        assertEquals("", stop.getStop_desc());
        assertNotEquals(null,stop.getStop_desc());

        assertEquals(43.0444475, stop.getStop_lat());
        assertNotEquals(null, stop.getStop_lat());

        assertEquals(-87.9779369, stop.getStop_lon());
        assertNotEquals(null, stop.getStop_lon());

    }

    //STOPTIME TESTS

    /**
     * This set tests what happens if fields are missing
     * @author Sofia Ricker, David Waas
     *
     */
    @Test
    public void testMissingRequiredElements() {
        // Make sure it does not allow if there is not the required elements
        // For this first one it tests what happens if there is no trip ID
        Assertions.assertFalse(fileIO.validateStopTimeImport(
                ",08:51:00,08:51:00,9113,1,,0,0"));
        //Checks what happens if there is no stop ID
        Assertions.assertFalse(fileIO.validateStopTimeImport(
                "21736564_2535,08:51:00,08:51:00,,1,,0,0"));
        //checks what happens if there is no head sign
        Assertions.assertFalse(fileIO.validateStopTimeImport(
                "21736564_2535,08:51:00,08:51:00,9113,,,0,0"));
        //checks what happens if there is no departure time
        Assertions.assertFalse(fileIO.validateStopTimeImport(
                "21736564_2535,,08:51:00,9113,1,,0,0"));
        //if a non required element is missing will it still work?
        Assertions.assertTrue(fileIO.validateStopTimeImport(
                "21736564_2535,08:51:00,08:51:00,9113,1,,0,"));
    }

    /**
     * This set tests what happens if there are extra or missing fields in the file
     * @author Sofia Ricker
     *
     */
    @Test
    public void testMissingStopTimeElements() {
        // Make sure extra/missing elements will return false
        assertFalse(fileIO.validateStopTimeImport(
                "21736564_2535,08:51:00,9113,1,,0,0"));
        assertFalse(fileIO.validateStopTimeImport(
                "21736564_2535,08:51:00,08:51:00,9113,1,,0,0,extra element, extra"));
        assertFalse(fileIO.validateStopTimeImport(
                "21736564_2535,08:51:00,08:51:00,9113,1,,0,0,extra element"));
    }

    /**
     * This set tests what happens if there are incorrect strings
     * @author Sofia Ricker, David Waas
     *
     */
    @Test
    public void testIncorrectTypeStopTimeElements() {
        // Make sure that a string cannot be passed into a place of an integer
        // Test to make sure it works with nothing changed
        Assertions.assertTrue(fileIO.validateStopTimeImport(
                "21736564_2535,08:52:00,08:52:00,4664,2,,0,0"));
        //This replaces every field with 'hi' individually to test what happens
        Assertions.assertFalse(fileIO.validateStopTimeImport(
                "hi,08:52:00,08:52:00,4664,2,,0,0"));
        Assertions.assertFalse(fileIO.validateStopTimeImport(
                "21736564_2535,hi,08:52:00,4664,2,,0,0"));
        Assertions.assertFalse(fileIO.validateStopTimeImport(
                "21736564_2535,08:52:00,hi,4664,2,,0,0"));
        Assertions.assertFalse(fileIO.validateStopTimeImport(
                "21736564_2535,08:52:00,08:52:00,hi,2,,0,0"));
        Assertions.assertFalse(fileIO.validateStopTimeImport(
                "21736564_2535,08:52:00,08:52:00,4664,hi,,0,0"));
        Assertions.assertFalse(fileIO.validateStopTimeImport(
                "21736564_2535,08:52:00,08:52:00,4664,2,,hi,0"));
        Assertions.assertFalse(fileIO.validateStopTimeImport(
                "21736564_2535,08:52:00,08:52:00,4664,2,,0,hi"));
    }
}

