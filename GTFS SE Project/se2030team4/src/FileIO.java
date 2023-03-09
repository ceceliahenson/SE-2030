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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hensonc
 * @version 1.0
 * @created 05-Oct-2022 10:08:47 AM
 */
public class FileIO {

    private FileWriter fileWriter;

    public FileIO() {

    }


    /**
     * read from a file the contains routes
     * @param file - selected file from user
     * @author Sofia
     */
    public ArrayList<String> readRoutes(File file) throws FileNotFoundException {
        ArrayList<String> routes = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        //goes through each line for file which each represent a route
        while (scanner.hasNextLine()) {
            routes.add(scanner.nextLine());

        }
        return routes;
    }

    /**
     * Ethan
     *
     * @param file
     */
    public ArrayList<String> readStops(File file) throws FileNotFoundException {
        ArrayList<String> stops = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            stops.add(scanner.nextLine());

        }
        return stops;
    }

    /**
     * David
     *
     * @param file
     */
    public ArrayList<String> readStopTimes(File file) throws FileNotFoundException {
        ArrayList<String> stopTimes = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            stopTimes.add(scanner.nextLine());

        }
        return stopTimes;
    }

    /**
     * Cece
     *
     * @param file
     */
    public ArrayList<String> readTrips(File file) throws FileNotFoundException {
        ArrayList<String> readTrips = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            readTrips.add(scanner.nextLine());

        }
        return readTrips;
    }

    public boolean validateStopsHeader(String line) {
        return line.equals("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
    }

    public boolean validateTripsHeader(String line) {
        return line.equals("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
    }

    public boolean validateStopTimesHeader(String line) {
        return line.equals("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type");
    }

    /**
     * @author Joe Kluding
     * @param line First line from routes in String form.
     * @return boolean
     * Below code validates that the header line in imported Routes file is correct.
     */
    public boolean validateRoutesHeader(String line) {
        return line.equals("route_id,agency_id,route_short_name," +
                "route_long_name,route_desc,route_type,route_url,route_color,route_text_color");
    }


    /**
     * will go through all lines stored in ArrayList and write them to a file
     * @param routes - each line of file representing a route
     * @param path - selected file path from user
     * @author Sofia
     */
    public void writeRoutes(ArrayList<String> routes, File path) throws IOException {
        System.out.println(Path.of(String.valueOf(path)));
        FileWriter writer = new FileWriter(String.valueOf(Path.of(String.valueOf(path))) + "\\routes.txt");
        writer.write("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color\n");
        for (String route : routes) {
            writer.append(route + "\n");
        }
        writer.close();
    }

    /**
     * will go through all lines stored in ArrayList and write them to a file
     * @param stops - each line of file representing a stop
     * @param path - selected file path from user
     * @author Sofia
     */
    public void writeStops(ArrayList<String> stops, File path) throws IOException {
        FileWriter writer = new FileWriter(String.valueOf(Path.of(String.valueOf(path))) + "\\stops.txt");
        writer.write("stop_id,stop_name,stop_desc,stop_lat,stop_lon\n");
        for (String stop : stops) {
            writer.append(stop + "\n");
        }
        writer.close();
    }

    /**
     * will go through all lines stored in ArrayList and write them to a file
     * @param stopTimes - each line of file representing a stopTime
     * @param path - selected file path from user
     * @author Sofia
     */
    public void writeStopTimes(ArrayList<String> stopTimes, File path) throws IOException {
        FileWriter writer = new FileWriter(String.valueOf(Path.of(String.valueOf(path))) + "\\stoptimes.txt");
        writer.write("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type\n");
        for (String stopTime : stopTimes) {
            writer.append(stopTime + "\n");
        }
        writer.close();
    }

    /**
     * will go through all lines stored in ArrayList and write them to a file
     * @param trips - each line of file representing a trip
     * @param path - selected file path from user
     * @author Sofia
     */
    public void writeTrips(ArrayList<String> trips, File path) throws IOException {
        FileWriter writer = new FileWriter(String.valueOf(Path.of(String.valueOf(path))) + "\\trips.txt");
        writer.write("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id\n");
        for (String trip : trips) {
            writer.append(trip + "\n");
        }
        writer.close();
    }

    /**
     * @author Joe Kluding
     * @param routes Full ArrayList of routes file
     * @return boolean
     * Below code checks to see if all fields in every line of imported Routes file are valid.
     */
    public boolean routesValidate(ArrayList<String> routes) {
        for (int i = 1; i < routes.size(); i++) {
            String routesCSV = routes.get(i);
            if(routes.get(i) == null){
                return false;
            }
            String[] CSVSplit = routesCSV.split(",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))", 12);
            int extra = 12-CSVSplit.length;
            String[] routeElements = Arrays.copyOf(CSVSplit,CSVSplit.length + extra);
            try {
                Integer.parseInt(routeElements[5]);
            } catch (NumberFormatException e) {
                    return false;
            }
            if(routeElements[9]!=null){
                try {
                    Integer.parseInt(routeElements[9]);
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            if(routeElements[10]!=null){
                try {
                    Integer.parseInt(routeElements[10]);
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            if(routeElements[11]!=null){
                try {
                    Integer.parseInt(routeElements[11]);
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            if (routeElements[0]==null||routeElements[0].isEmpty()) {
                return false;
            } else if (0 > Integer.parseInt(routeElements[5])
                    || Integer.parseInt(routeElements[5]) > 12) {
                return false;
            } else if ((routeElements[6]!=null&&!routeElements[6].isEmpty())
                    && !isValidURL(routeElements[6])) {
                return false;
            } else if ((routeElements[7]!=null&&!routeElements[7].isEmpty())
                    && colorChecker(routeElements[7])) {
                return false;
            } else if ((routeElements[8]!=null&&!routeElements[8].isEmpty())
                    && colorChecker(routeElements[8])) {
                return false;
            } else if ((routeElements[9]!=null&&!routeElements[9].isEmpty())
                    && 0 > Integer.parseInt(routeElements[9])) {
                return false;
            } else if ((routeElements[10]!=null&&!routeElements[10].isEmpty())
                    && (0 > Integer.parseInt(routeElements[10])
                    || Integer.parseInt(routeElements[10]) > 3)) {
                return false;
            } else if ((routeElements[11]!=null&&!routeElements[11].isEmpty())
                    && (0 > Integer.parseInt(routeElements[11])
                    || Integer.parseInt(routeElements[11]) > 3)) {
                return false;
            }
        }
        return true;
    }



    /**
     * verifies that the line is okay to be added to the list of StopTimes
     * Testing
     * @param line
     * @return
     * @author David Waas
     */
    public boolean validateStopTimeImport(String line) {
        int c = 0;
        for(int i=0; i<line.length(); i++)
        {
            if(line.charAt(i) == ',')
                c++;
        }
        if(c != 7){
            return false;
        }
        String[] params = line.split(",");
        for (int i = 0; i <= params.length - 1; i++) {
            if (i == 0) {
                if(params[i].length() == 0||params[i].isEmpty()) {
                    return false;
                }
            } else if (i == 1 || i == 2) {
                if((!params[1].isEmpty())&&params[2].isEmpty())
                    params[2] = params[1];
                if (params[i].length() != 8) {
                    return false;
                }
            } else if (i == 3) {
                if (params[i].length() == 0||params[i].isEmpty()){
                    return false;
                }
            } else if (i == 4) {
                if(params[i].isEmpty()) {
                    return false;
                }
                try {
                    Integer.parseInt(params[i]);
                } catch (NumberFormatException e) {
                    return false;
                }
            } else if (i == 6 || i == 7) {
                if (!(params[i].equals("0") || params[i].equals("1") || params[i].equals("2") || params[i].equals("3"))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * verifies that the line is okay to be added to the list of trips
     * Testing
     *
     * @param line
     * @return
     * @author Ethan Jeffers
     */
    public  String[] validateTripImport(String line) {
        // Remember to do the comma thing
        // Optional parameters: headsign, short name, directionID, blockID, serviceID
        // Required: Route_ID, trip_ID, shapeID
        String[] lines = null;
        if (line.equals("")) {
            return null;
        }
        if (!line.contains("\"")) {
            // checks to see if we even have to worry about there being commas within elements
            // since that is the case, it will then check to see if there is even a valid # of commas
            if (validNumberOfCommas(line)) {
                lines = line.split(",");
                //Makes sure that integers are passed into correct elements and requirements are met
                if (!validateRequirements(lines, line)) {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            if (numberOfQuotes(line) % 2 == 0) {
                // headsign is the only valid element that can contain quotes
                lines = line.split(",");
                // If there is quotes in the file, and the quotes are not a part of headsign
                // the file is not valid since the only valid spot for a quote is in headsign.
                if (lines[0].contains("\"") || lines[2].contains("\"") || lines[1].contains("\"")
                        || !lines[3].startsWith("\"")) {
                    return null;
                } else if (lines[3].startsWith("\"")) {
                    //This is a valid quote
                    int first = line.indexOf('\"');
                    int second = line.indexOf('\"', first + 1);
                    lines[3] = line.substring(first, second + 1);
                    String restOfLine = line.substring(second + 2);
                    String[] parts2 = restOfLine.split(",");
                    lines[4] = parts2[0];
                    lines[5] = parts2[1];
                    lines[6] = parts2[2];
                    if (!validateRequirements(lines, line)) {
                        return null;
                    }
                }
            } else {
                return null;
            }
        }

        // Check the route ID validity
        return lines;
    }

    private int numberOfQuotes(String line) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '\"') {
                count++;
            }
        }
        return count;
    }

    private boolean validateRequirements(String[] lines, String line) {
        if (lines[0].equals("")) {
            return false;
        } else if (lines[2].equals("")) {
            return false;
        } else if (line.endsWith(",")) {
            return false;
        } else if ((lines[4].length() != 0 && lines[5].length() != 0) &&
                (!lines[4].matches("[0-9]+") || !lines[5].matches("[0-9]+"))) {
            return false;
        } else if (lines[4].length() != 0 && (!lines[4].equals("1") && !lines[4].equals("0"))) {
            return false;
        }
        return true;
    }

    private static boolean validNumberOfCommas(String line) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ',') {
                count++;
            }
        }
        return count == 6;
    }

    /**
     * @author Joe Kluding
     * @param url String of potential URL passed in.
     * @return boolean
     * Below is helper function to assist in checking if a String is a valid URL.
     */
    public boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    /**
     * @author Joe Kluding
     * @param color Potential color code passed in as a String
     * @return boolean
     * Below is a helper function to assist in checking if a String is a valid color code.
     */
    public boolean colorChecker(String color) {
        Pattern pattern = Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})");
        Matcher matcher = pattern.matcher(color);
        return (matcher.matches());
    }

    /**
     * @Author: Cece
     *
     * Method validates the lines in the stop files
     * being imported
     * @param lines - stop lines
     * @return boolean
     */
    public boolean validateStopImport(String lines){

        String[] line = lines.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        if(line.length != 5){
            return false;
        }
        if(line[1].contains("\"")){
            return true;
        }
        if(line[2].contains("\"") ){
            return true;
        }
        if(line[0].matches("^[a-z_\\-]+$")){
            return false;
        }
        if(line[3].matches(".*[a-z].*")
                || line[4].matches(".*[a-z].*")){
            return false;
        }

        if(line[3].matches(".*[A-Z].*")
                || line[4].matches(".*[A-Z].*")){
            return false;
        }

        if(!line[3].matches("^(-?(\\d+)?\\.\\d+)$")){
            return false;
        }
        if(!line[4].matches("^(-?(\\d+)?\\.\\d+)$")){
            return false;
        }
        if(Double.parseDouble(line[3]) < -90){
            return false;
        }
        if(Double.parseDouble(line[3]) > 90.0){
            return false;
        }

        if(Double.parseDouble(line[4]) > 180){
            return false;
        }
        if(Double.parseDouble(line[4]) < -180){
            return false;
        }


        return true ;
    }

}