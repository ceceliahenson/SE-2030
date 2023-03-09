/*Copyright 2022 SE2030Fall2022Group4

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

import javafx.scene.control.Alert;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author hensonc
 * @version 1.0
 * @created 05-Oct-2022 10:08:52 AM
 */
public class GTFSSubject implements Subject {

    private ArrayList<Observer> observers;
    private ArrayList<String> dataToSend;

    public HashMap<String, List<Route>> getRoutes() {
        return routes;
    }

    public HashMap<String, List<Stop>> getStops() {
        return stops;
    }

    public HashMap<String, List<StopTime>> getStopTimes() {
        return stopTimes;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    private HashMap<String, List<Route>> routes;
    private HashMap<String, List<Stop>> stops;
    private HashMap<String, List<StopTime>> stopTimes;
    private ArrayList<Trip> trips;
    private FileIO fileIO;


    public GTFSSubject(ArrayList<Observer> observers) {
        this.observers = observers;
        this.routes = new HashMap<>();
        this.stops = new HashMap<>();
        this.stopTimes = new HashMap<>();
        this.trips = new ArrayList<>();
        fileIO = new FileIO();
    }

    /**
     * @param gtfsAttributes Arraylist<String>
     * @author Sofia
     * verifies if valid route file and creates arraylist of routes
     * Edited 10/17/22 by Joe Kluding
     * Added validation and error message.
     */
    public void createRoutes(ArrayList<String> gtfsAttributes) {
        if (fileIO.validateRoutesHeader(gtfsAttributes.get(0))
                && fileIO.routesValidate(gtfsAttributes)) {
            gtfsAttributes.remove(0);
            routes.clear();
            for (String line : gtfsAttributes) {
                String[] routeAtt = line.split(",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))");
                Route route;
                if (routeAtt.length == 8) {
                    route = new Route(routeAtt[0], routeAtt[1], routeAtt[2], routeAtt[3], routeAtt[4], Integer.parseInt(routeAtt[5]), routeAtt[6], routeAtt[7], "");
                } else {
                    route = new Route(routeAtt[0], routeAtt[1], routeAtt[2], routeAtt[3], routeAtt[4], Integer.parseInt(routeAtt[5]), routeAtt[6], routeAtt[7], routeAtt[8]);
                }
                addRoute(routeAtt[0], route);
            }

            System.out.println("Routes have been added");

        } else {
            JPanel jpanel = new JPanel();
            JOptionPane.showMessageDialog(jpanel, "Could not validate routes file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRoute(String key, Route value) {
        if (routes.containsKey(key)) {
            routes.get(key).add(value);
        } else {
            routes.put(key, new ArrayList<>());
            routes.get(key).add(value);
        }
    }


    /**
     * author: cece
     * verifies if valid trips file and creates arraylist of trips
     * @param gtfsAttributes
     */
    public void createTrips(ArrayList<String> gtfsAttributes) {
        if (fileIO.validateTripsHeader(gtfsAttributes.get(0))) {
            gtfsAttributes.remove(0);
            trips.clear();
            for (String line : gtfsAttributes) {
                String[] tripAtt = fileIO.validateTripImport(line);
                if (tripAtt != null) {
                    Trip trip = new Trip(tripAtt[0], tripAtt[1], tripAtt[2],
                            tripAtt[3], determineParameter(tripAtt[4]), determineLong(tripAtt[5]), tripAtt[6]);
                    trips.add(trip);
                }
            }
            System.out.println("Trips have been added");
        }
    }

    /**
     * Used to make sure that the program is not trying to parse a blank space
     * @author: Ethan Jeffers
     * @param part
     * @return
     */
    private long determineLong(String part) {
        return part.length() > 0 ? Long.parseLong(part) : -999;
    }

    private int determineParameter(String part) {
        return part.length() > 0 ? Integer.parseInt(part) : -999;
    }

    /**
     * author:David
     * verifies if valid stop-time file and creates arraylist of stop-times
     * @param gtfsAttributes
     */
    public void createStopTimes(ArrayList<String> gtfsAttributes) {
        if (fileIO.validateStopTimesHeader(gtfsAttributes.get(0))) {
            gtfsAttributes.remove(0);
            stopTimes.clear();
            for (String line : gtfsAttributes) {
                if (fileIO.validateStopTimeImport(line)) {
                    String[] routeAtt = line.split(",");
                    if (routeAtt.length == 5) {
                        StopTime stopTime = new StopTime(routeAtt[0], routeAtt[1], routeAtt[2],
                                routeAtt[3], Integer.parseInt(routeAtt[4]), "",
                                0, 0);
                        addStopTime(routeAtt[3], stopTime);
                    } else if (routeAtt.length == 6) {
                        StopTime stopTime = new StopTime(routeAtt[0], routeAtt[1], routeAtt[2],
                                routeAtt[3], Integer.parseInt(routeAtt[4]), routeAtt[5],
                                0, 0);
                        addStopTime(routeAtt[3], stopTime);
                    } else {
                        StopTime stopTime = new StopTime(routeAtt[0], routeAtt[1],
                                routeAtt[2], routeAtt[3], Integer.parseInt(routeAtt[4]), routeAtt[5],
                                Integer.parseInt(routeAtt[6]), Integer.parseInt(routeAtt[7]));
                        addStopTime(routeAtt[3], stopTime);
                    }
                }
            }
            System.out.println("StopTimes have been added");
        }
    }

    /**
     * Determines if an instance of StopTime with the stopID exists, and adds it accordingly
     *
     * @param key
     * @param value
     * @author Ethan Jeffers
     */
    private void addStopTime(String key, StopTime value) {
        if (stopTimes.containsKey(key)) {
            stopTimes.get(key).add(value);
        } else {
            stopTimes.put(key, new ArrayList<>());
            stopTimes.get(key).add(value);
        }
    }


    /**
     * Author: Ethan Jeffers
     * Parses the stop file and adds it to a data structure
     * @param gtfsAttributes
     */
    public void createStops(ArrayList<String> gtfsAttributes) {
        stops.clear();
        for (String line : gtfsAttributes) {
            fileIO.validateStopImport(line);
            String[] stopAtt = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            addStop(stopAtt[0], (new Stop(stopAtt[0], stopAtt[1], stopAtt[2], Double.parseDouble(stopAtt[3]),
                    Double.parseDouble(stopAtt[4]))));
        }
        System.out.println("Stops have been added");
    }

    private void addStop(String key, Stop value) {
        if (stops.containsKey(key)) {
            stops.get(key).add(value);
        } else {
            stops.put(key, new ArrayList<>());
            stops.get(key).add(value);
        }
    }

    /**
     * author: cece
     * this method validates if the users entered stop_id is in the stops file
     * returns false if not valid
     * @param stop_id - stop id
     * @return if valid stop_id
     */
    public boolean validateStopId(String stop_id){
        boolean valid = false;
        for(int i = 0; i < stops.get(stop_id).size(); i++){
            if(stopTimes.get(stop_id).get(i).getStop_id().equals(stops.get(stop_id).get(i).getStop_ID())){
                valid = true;
            }
        }
        return valid;
    }

    /**
     * author: cece
     * this method checks the users route input and checks
     * if it is a valid one before preforming the search
     * @param route_id route that the user enters
     * @return if the route is valid or not
     */
    public boolean validateRouteId(String route_id){
        boolean valid = false;
        for(int i = 0; i < routes.get(route_id).size(); i++){
            if(routes.containsKey(route_id)){
                valid = true;
            }
        }
        return valid;
    }

    // feature 6
    /**
     * author: cece
     * this method searches the given route id from the user
     * and returns the list of stops associates to that
     * specific route
     * @param route_id route id the use enters
     */
    public void searchStopWithRouteId(String route_id){
        ArrayList<String> stops = new ArrayList<>();

        for(Trip trip: trips){
            // checks if route id is in trips file
            if(route_id.equals(trip.getRoute_ID())){
                for(Map.Entry<String, List<StopTime>> set : stopTimes.entrySet()){
                    for(int i = 0; i < set.getValue().size(); i++) {
                        // checks if trip id is in stops time
                        if (trip.getTrip_ID().equals(set.getValue().get(i).getTrip_id())) {
                            // checks for repeating stops before adding to stops list
                            if(!stops.contains(set.getValue().get(i).getStop_id())){
                                stops.add(set.getValue().get(i).getStop_id());
                            }
                        }
                    }
                }
            }
        }

        notifyObserver(stops);
    }
    // end of feature 6

    // feature 5
    /**
     * author: cece
     * Method searches the routes that contain stop_id
     *
     * @param stop_id - user stop_id input
     */
    public void searchRouteWithStopId(String stop_id) {

        ArrayList<String> trip_ids = searchTripsWithStopId(stop_id);
        ArrayList<String> route_ids = new ArrayList<>();

        for(Trip trip : trips){
            if(trip_ids.contains(trip.getTrip_ID())){
                if(!route_ids.contains("With Stop " + stop_id + " it is on route: " + trip.getRoute_ID())) {
                    route_ids.add("With Stop " + stop_id + " it is on route: " + trip.getRoute_ID());
                }
            }
        }

        notifyObserver(route_ids);
    }

    // end of feature 5

    // feature 4
    /**
     * searches for the trips on a specific stopId
     *
     * @param stop_id - stop_id entered by user
     * @return - trips that use that stop_id
     * @author Sofia and Ethan
     */
    public ArrayList<String> searchTripsWithStopId(String stop_id) {
        ArrayList<String> trips = new ArrayList<>();
        for (int i = 0; i < stopTimes.get(stop_id).size(); i++) {
                trips.add(stopTimes.get(stop_id).get(i).getTrip_id());
        }
        notifyObserver(trips);
        return trips;
    }

    // end of feature 4

    // feature 7
    /**
     * searches for future trips by route ID
     *
     * @param - route_id - route_id entered by user
     * @return - trips that use that route_id
     * @author Dave
     */

    public void searchTripsWithRouteId(String route_id) {
        ArrayList<String> trip = new ArrayList<>();
        for(Trip t: trips){
            if(t.getRoute_ID().equals(route_id))
                trip.add(t.getTrip_ID());
        }
        notifyObserver(trip);
    }
    // end of feature 7

    // feature 8
    /**
     * searches through the trip_ids associated with the entered stop and finds the next trip
     *
     * @param stop_id - the entered stop_id from the user
     * @author Sofia and Ethan
     */
    public void searchNextTripWithStopId(String stop_id) {
        //get the list of trip_ids associated with that stop_id
        ArrayList<String> trip_ids = searchTripsWithStopId(stop_id);
        //gets the current date and time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        //separates time from date
        String time = (dtf.format(now)).split(" ")[1];
        int nearestTime = 999999;
        ArrayList<String> nearestTrip = new ArrayList<>();
        int formattedTime = formatTime(time);
        //loops through stopTimes and stores trip_id that is next compared to current time
        for (Map.Entry<String, List<StopTime>> set : stopTimes.entrySet()) {
            for (int i = 0; i < set.getValue().size(); i++) {
                if (trip_ids.contains(set.getValue().get(i).getTrip_id())) {
                    int tripTime = formatTime(set.getValue().get(i).getDeparture_time());
                    if (formattedTime <= tripTime && tripTime <= nearestTime) {
                        if (tripTime == nearestTime) {
                            nearestTrip.add(set.getValue().get(i).getTrip_id());
                        } else {
                            nearestTrip.clear();
                            nearestTrip.add(set.getValue().get(i).getTrip_id());
                        }
                        nearestTime = tripTime;
                    }
                }
            }
        }
        //send to display on observer
        if (nearestTrip.isEmpty()) {
            nearestTrip.add("No more trips on this stop today.");
        }
        notifyObserver(nearestTrip);
    }

    /**
     * displays the number of trips on every stop
     *
     * @author Ethan and Sofia
     */
    public void displayNumTrips() {
        ArrayList<String> toAdd = new ArrayList<>();
        for (Map.Entry<String, List<Stop>> set : stops.entrySet()) {
            int numTrips = 0;
            if (stopTimes.containsKey(set.getKey())) {
                for (int i = 0; i < set.getValue().size(); i++) {
                    numTrips += stopTimes.get(set.getValue().get(i).getStop_ID()).size();
                    toAdd.add("Stop: " + set.getValue().get(i).getStop_ID() + " - " + numTrips + "\n");
                }
            }
        }
        notifyObserver(toAdd);
    }

    /**
     * formats the time to be comparable, changes it from a string to an int
     *
     * @param time - string time in format XX:XX:XX
     * @return the time as an int with colons removed
     */
    private int formatTime(String time) {
        String formattedTime = "";
        for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) != ':') {
                formattedTime += time.charAt(i);
            }
        }
        return Integer.parseInt(formattedTime);
    }

    // end of feature 8

    /**
     * @param o
     */
    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    /**
     * @param o
     */
    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }


    /**
     * used to display items in the text field in the GUI
     *
     * @param args
     * @author Ethan Jeffers
     */
    @Override
    public void notifyObserver(Collection args) {
        for (Observer o : observers) {
            o.update(args);
        }
    }

    //not implemented

    /**
     * Allows for dragging on a stop to change the stop location.
     * UNIMPLEMENTED
     *
     * @param stop_id
     */
    public void clickStopMove(String stop_id) {

    }


    /**
     * Will plot the bus location based on the current time
     * UNIMPLEMENTED
     *
     * @param trip_id
     */
    public void plotBus(String trip_id) {

    }

    /**
     * Plots the stops on Google Maps given the route ID
     * UNIMPLEMENTED
     *
     * @param route_id
     */
    public void plotStops(String route_id) {

    }


    /**
     * Allows updates of stops attributes simultaneously instead of one by one.
     * UNIMPLEMENTED
     *
     * @param stop_ids
     */
    public void updateGroupStop(ArrayList<String> stop_ids) {

    }


    /**
     * Allows for an update on a stop attributed given the corresponding stop ID.
     * UNIMPLEMENTED
     *
     * @param stop_id
     */
    public void updateStopAtt(String stop_id) {

    }


    public void clickStopChangeTime() {

    }

}