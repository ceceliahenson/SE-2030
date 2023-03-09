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


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    public Button trips;
    @FXML
    public Button routes;
    @FXML
    public Button nextTrip;
    @FXML
    public Button routesStop;
    @FXML
    public Button routesTrip;
    Alert a = new Alert(Alert.AlertType.NONE);
    public TextField stopIdForTrips;
    public TextField routesForStop;
    private FileChooser fileChooser = new FileChooser();
    private FileIO fileIO = new FileIO();
    private GTFSSubject gtfsSubject;
    Observer listGUI;
    Observer mapGUI;



    @FXML
    private Label legend;

    @FXML
    private TextField mapsSearchBar;

    @FXML
    private Button plot;

    @FXML
    private MenuItem export;

    @FXML
    private MenuItem mapScene;

    @FXML
    private MenuItem tableScene;

    @FXML
    public Label text;

    public Controller(){
    }

    // feature 15

    /**
     * will collect information on routes, stops, stoptimes, and trips stored in the gtfs subjects and write that information
     * to a file
     * @author Sofia
     * @param event - export button clicked
     */
    @FXML
    void exportFile(ActionEvent event) {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selected = directoryChooser.showDialog(null);
            //sending array to fileIO for Routes
            HashMap<String, List<Route>> routes = gtfsSubject.getRoutes();
            ArrayList<String> routeLines = new ArrayList<>();
            for (Map.Entry<String, List<Route>> set : routes.entrySet()) {
                for (int i = 0; i < set.getValue().size(); i++) {
                    String line = set.getValue().get(i).toString();
                    routeLines.add(line);
                }
            }
            fileIO.writeRoutes(routeLines, selected);

            //sending array to fileIO for Stop objects
            HashMap<String, List<Stop>> stops = gtfsSubject.getStops();
            ArrayList<String> stopLines = new ArrayList<>();
            for (Map.Entry<String, List<Stop>> set : stops.entrySet()) {
                for (int i = 0; i < set.getValue().size(); i++) {
                    String line = set.getValue().get(i).toString();
                    stopLines.add(line);
                }
            }
            fileIO.writeStops(stopLines, selected);

            //sending array to fileIO for StopTime objects
            HashMap<String, List<StopTime>> hm = gtfsSubject.getStopTimes();
            ArrayList<String> stopTimeLines = new ArrayList<>();
            for (Map.Entry<String, List<StopTime>> set : hm.entrySet()) {
                for (int i = 0; i < set.getValue().size(); i++){
                    stopTimeLines.add(set.getValue().get(i).toString());
                }
            }
            fileIO.writeStopTimes(stopTimeLines, selected);

            //sending array to fileIO for Trip objects
            ArrayList<Trip> trips = gtfsSubject.getTrips();
            ArrayList<String> tripLines = new ArrayList<>();
            for (Trip trip : trips) {
                String line = trip.toString();
                tripLines.add(line);
            }
            fileIO.writeTrips(tripLines, selected);



        } catch (IOException e) {
            System.out.println("There was a problem exporting your files");
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gtfsSubject = new GTFSSubject(new ArrayList<>());
        listGUI = new ListViewGUI(gtfsSubject, this);
        mapGUI = new MapsGUI(gtfsSubject);
    }


    // feature 1

    /**
     * author: sofia
     * this method allows the user to select a directory that they want to upload
     * with all of the gtfs files; throws exception if incorrect file tyes are selected
     * @param event open button
     */
    @FXML
    void openFile(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(null);
        File[] files = directory.listFiles();
        ArrayList<String> allLines = new ArrayList<>();
            try {
                //verifies directory contains all correct files
                if(files != null && verifyFilesInDirectory(files)) {
                    for (File file : files) {
                        if (file.toString().contains("routes")) {
                            allLines = fileIO.readRoutes(file);
                            gtfsSubject.createRoutes(allLines);
                        } else if (file.toString().contains("stop_times")) {
                            allLines = fileIO.readStopTimes(file);
                            gtfsSubject.createStopTimes(allLines);
                            // This if statement is just because there is so many route times in the default file,
                            // it does not crash the program trying to print all of them

                        } else if (file.toString().contains("trips")) {
                            allLines = fileIO.readTrips(file);
                            gtfsSubject.createTrips(allLines);
                        } else if (file.toString().contains("stops")) {
                            allLines = fileIO.readStops(file);
                            fileIO.validateStopsHeader(allLines.get(0));
                            allLines.remove(0);
                            gtfsSubject.createStops(allLines);
                        }
                    }
                    if (gtfsSubject.getStopTimes().size() > 0 && gtfsSubject.getStops().size() > 0
                            && gtfsSubject.getTrips().size() > 0 && gtfsSubject.getTrips().size() > 0){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Files Added");
                        alert.setHeaderText("Files have been sucessfully added");
                        alert.show();
                    } else{
                        a.setAlertType(Alert.AlertType.ERROR);
                        a.setHeaderText("Upload Error");
                        a.setContentText("Something was wrong with one or more of your files");
                    }
                } else {
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setContentText("You are missing some files. Please select a different directory.");
                    a.show();
                }
                trips.setDisable(false);
                routes.setDisable(false);
                nextTrip.setDisable(false);
                routesStop.setDisable(false);
                routesTrip.setDisable(false);
                export.setDisable(false);

            } catch (FileNotFoundException e){
               a.setAlertType(Alert.AlertType.ERROR);

                // set content text
                a.setContentText("File type is invalid");

                // show the dialog
                a.show();
                //FileNotFoundException
            }



    }

    /**
     * verifies that all the required GTFS files are inside the selected directory
     * @author Sofia
     * @param files - array of all the files in the select directory
     * @return - true is contains all required files
     */
    private boolean verifyFilesInDirectory(File[] files) {
        boolean containsStops = false;
        boolean containsTrips = false;
        boolean containsStopTimes = false;
        boolean containsRoutes = false;
        //loops through all files and checks that certain files one of the files in the array
        for(File file : files){
            if(file.toString().contains("trips") ){
                containsTrips = true;
            } else if(file.toString().contains("routes")){
                containsRoutes = true;
            } else if(file.toString().contains("stop_times")){
                containsStopTimes = true;
            } else if(file.toString().contains("stops")){
                containsStops = true;
            }
        }
        return containsRoutes && containsTrips && containsStopTimes && containsStops;
    }

    /**
     * method gets user stop id choice and calls method in GTFS subject
     * @author Sofia
     * @param actionEvent action
     */
    @FXML
    public void searchTripsWithStopId(ActionEvent actionEvent) {
        try {
            if (gtfsSubject.validateStopId(stopIdForTrips.getText())) {
                gtfsSubject.searchTripsWithStopId(stopIdForTrips.getText());
            }

        }catch(Exception e){
            a.setAlertType(Alert.AlertType.ERROR);
            // set content text
            a.setContentText("Invalid stop_id please enter a valid one ");
            // show the dialog
            a.show();
        }

    }

    /**
     * author: cece
     * this method is the on action for the button find route
     * where it will go the gtfs subject and preform
     * the search of the stops associated to the route
     * entered
     * @param actionEvent button push
     */
    @FXML
    public void searchStopWithRouteId(ActionEvent actionEvent){
        try {
            if(gtfsSubject.validateRouteId(routesForStop.getText())) {
                gtfsSubject.searchStopWithRouteId(routesForStop.getText());
            }
        }catch (Exception e){
            a.setAlertType(Alert.AlertType.ERROR);
            // set content text
            a.setContentText("Invalid route_id please enter a valid one ");
            // show the dialog
            a.show();
        }
    }



    /**
     * method gets user stop id choice and calls method in GTFS subject
     * @author Sofia
     * @param actionEvent action
     */
    @FXML
    public void searchNextTripWithStopId(ActionEvent actionEvent) {
        try {
            if (gtfsSubject.validateStopId(stopIdForTrips.getText())) {
                gtfsSubject.searchNextTripWithStopId(stopIdForTrips.getText());
            }

        }catch(Exception e){
            a.setAlertType(Alert.AlertType.ERROR);
            // set content text
            a.setContentText("Invalid stop_id please enter a valid one ");
            // show the dialog
            a.show();
        }

    }

    /**
     * method gets user stop id choice and calls method in GTFS subject
     * @author Ethan
     * @param actionEvent action
     */
    public void displayNumTrips(ActionEvent actionEvent) {
        gtfsSubject.displayNumTrips();
    }

    /**
     * author: cece
     * method gets user stop id choice
     * @param actionEvent action
     */

    @FXML
    public void searchRouteWithStopId(ActionEvent actionEvent){
        try {
            if (gtfsSubject.validateStopId(stopIdForTrips.getText())) {
                gtfsSubject.searchRouteWithStopId(stopIdForTrips.getText());
            }

        }catch(Exception e){
            a.setAlertType(Alert.AlertType.ERROR);
            // set content text
            a.setContentText("Invalid stop_id please enter a valid one ");
            // show the dialog
            a.show();
        }
    }

    /**
     * author: dave
     * method gets users route, and shows trips on that route
     * @param actionEvent action
     */
    @FXML
    public void searchTripWithRouteId(ActionEvent actionEvent){
        try {
            if (gtfsSubject.validateRouteId(routesForStop.getText())) {
                gtfsSubject.searchTripsWithRouteId(routesForStop.getText());
            }

        }catch(Exception e){
            a.setAlertType(Alert.AlertType.ERROR);
            // set content text
            a.setContentText("Invalid Route please enter a valid one ");
            // show the dialog
            a.show();
        }
    }


    @FXML
    public void viewMap(){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Maps.fxml"));
                MapsController.defineAttributes(gtfsSubject.getRoutes(), gtfsSubject.getStops(), gtfsSubject.getTrips(),
                        gtfsSubject.getStopTimes());
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root1);
                stage.setScene(scene);
                stage.show();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }


    }