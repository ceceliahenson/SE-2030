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

/**
 * Class:SE 2030 021
 * Dr. Riley
 * Group Five
 * Date:
 **/

import java.util.InputMismatchException;

/**
 * * Objects that represents a Stop Time and holds those attributes associated with a stop
 */
public class Stop {

    private String stop_desc;
    private String stop_ID;
    private double stop_lat;
    private double stop_lon;
    private String stop_name;


    /**
     * Uses the information from the GTFS file to set the attributes a stop time
     *
     * @param stop_desc - Description of the location that provides useful, quality information
     * @param stop_ID   - Identifies a stop, station, or station entrance.
     * @param stop_lat  - Latitude of the location
     * @param stop_lon  - Longitude of the location.
     * @param stop_name - Name of the location
     */
    public Stop(String stop_ID, String stop_name, String stop_desc, double stop_lat, double stop_lon) {
        this.stop_desc = stop_desc;
        this.stop_ID = stop_ID;
        this.stop_lat = stop_lat;
        this.stop_lon = stop_lon;
        this.stop_name = stop_name;
    }
    // think of everything that could go wrong in the line


    /**
     *
     *
     * @return
     * @author Sofia
     */
    @Override
    public String toString() {
        return this.stop_ID + "," + this.stop_name + "," + this.stop_desc + "," +
        this.stop_lat + "," + this.stop_lon;
    }

    public String getStop_desc() {
        return stop_desc;
    }
    public void setStop_desc(String stop_desc) {
        this.stop_desc = stop_desc;
    }
    public String getStop_ID() {
        return stop_ID;
    }
    public void setStop_ID(String stop_ID) {
        this.stop_ID = stop_ID;
    }
    public double getStop_lat() {
        return stop_lat;
    }
    public void setStop_lat(double stop_lat) {
        this.stop_lat = stop_lat;
        if (stop_lat > -90 && stop_lat < 90) {
            this.stop_lat = stop_lat;
        } else {
            throw new InputMismatchException("Latitude is not valid");
        }
    }
    public double getStop_lon() {
        return stop_lon;
    }
    public void setStop_lon(double stop_lon) {
        this.stop_lon = stop_lon;
        if (stop_lon > -180 && stop_lon < 180) {
            this.stop_lon = stop_lon;
        } else {
            throw new InputMismatchException("Longitude is not valid");
        }
    }
    public String getStop_name() {
        return stop_name;
    }
    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }


}