
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


import java.net.URL;
import java.util.*;

public class MapsController implements Initializable {

    @FXML
    private TextField textField;

    @FXML
    private Label legend;

    @FXML
    private WebView webView;
    private WebEngine webEngine;
    private static HashMap<String, List<Route>> routes;
    private static HashMap<String, List<Stop>> stops;
    private static List<Trip> trips;
    private static HashMap<String, List<StopTime>> stopTimes;
    private String currentColor;

    /**
     * Sets all of the data structures in this controller to the data structures that are
     * currently in the gtfs class.
     * @author: Ethan Jeffers
     * @param orgRoutes
     * @param orgStops
     * @param orgTrips
     * @param orgTimes
     */
    public static void defineAttributes(HashMap<String, List<Route>> orgRoutes, HashMap<String, List<Stop>> orgStops,
                                        ArrayList<Trip> orgTrips, HashMap<String, List<StopTime>> orgTimes) {
        routes = orgRoutes;
        stops = orgStops;
        trips = orgTrips;
        stopTimes = orgTimes;
    }

    /**
     * Given the routeID, this method will:
     * 1: Find all the trips with the routeID
     * 2: Look through stopTimes to find the tripID, and then added all stops that correlate
     * 3: Return the list of stops with the routeID
     * @author: Ethan Jeffers
     * @param routeID
     * @return
     * @throws IllegalArgumentException
     */
    private HashSet<String> findStops(String routeID) throws IllegalArgumentException{
        // Finds all trips with the associated routeID
        List<Trip> tripsWithRouteID = new ArrayList<>();
        for (Trip t : trips) {
            if (t.getRoute_ID().equals(routeID)) {
                tripsWithRouteID.add(t);
            }
        }

        if (tripsWithRouteID.size() == 0){
            throw new IllegalArgumentException("Route not found");
        }

        //Find all stops with associated tripID's
        HashSet<String> validStopIDs = new HashSet<>();
        for (Trip t : tripsWithRouteID) {
            for (Map.Entry<String, List<StopTime>> set : stopTimes.entrySet()) {
                for (int i = 0; i < set.getValue().size(); i++) {
                    if (set.getValue().get(i).getTrip_id().equals(t.getTrip_ID())) {
                        validStopIDs.add(set.getValue().get(i).getStop_id());
                    }
                }
            }
        }
        return validStopIDs;
    }

    /**
     * Will plot all of the stops of the given routeID
     * This method makes use of javascript code, the orinal code can be found here:
     * https://stackoverflow.com/questions/3059044/google-maps-js-api-v3-simple-multiple-marker-example
     * By rearranging and making my own method from this code, functions were able to be created within
     * the javascript code, which could be called from the webengine. This was used to plot the points and center map.
     * @author: Ethan Jeffers
     * @param actionEvent
     */
    @FXML
    void plot(ActionEvent actionEvent) {
        try {
            String legendText = "Route:\n";
            ArrayList<String> stopIDs = new ArrayList<>(findStops(textField.getText()));
            webEngine.executeScript("centralizeMap(" + stops.get(stopIDs.get(0)).get(0).getStop_lat() + "," +
                    stops.get(stopIDs.get(0)).get(0).getStop_lon() + ")");
            currentColor = routes.get(textField.getText()).get(0).getRoute_color();
            for (int i = 0; i < stopIDs.size(); i++) {
                double lon = stops.get(stopIDs.get(i)).get(0).getStop_lon();
                double lat = stops.get(stopIDs.get(i)).get(0).getStop_lat();
                String color = routes.get(textField.getText()).get(0).getRoute_color().toString();
                String name = stops.get(stopIDs.get(i)).get(0).getStop_name();
                currentColor = color;
                webEngine.executeScript("createMarker(" + lat + "," + lon + ", '" + name + "','" + color +"')");
            }
            legendText += textField.getText() + "\n\n" + "Color:\n" + currentColor;
            legend.setText(legendText);
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Route");
            alert.setHeaderText("Route entered does not exist.");
            alert.showAndWait();
        }
    }


    /**
     * This method makes use of the webview in the GUI to set the map up
     * To do this the webengine loads a html file, which can be found in the src folder.
     * @author: Ethan Jeffers
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL map = this.getClass().getResource("GoogleMaps.html");
        webEngine = webView.getEngine();
        webEngine.load(map.toExternalForm());
    }


}
