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

import java.sql.Time;


/**
 * @version 1.0
 * @created 05-Oct-2022 10:09:03 AM
 */
public class Trip {

    private long block_ID;
    private int direction_ID;
    private String route_ID;
    private String service_ID;
    private String shape_ID;
    private String trip_headsign;
    private String trip_ID;


    /**
     * Note: If the int being passed in does not exist the number -999 will be assigned to the variable
     * @param block_ID
     * @param direction_ID
     * @param route_ID
     * @param service_ID
     * @param shape_ID
     * @param trip_headsign
     * @param trip_ID
     * @author: cece
     */
    public Trip(String route_ID, String service_ID, String trip_ID, String trip_headsign,
                int direction_ID, long block_ID, String shape_ID) {
        this.route_ID = route_ID;
        this.service_ID = service_ID;
        this.trip_ID = trip_ID;
        this.trip_headsign = trip_headsign;
        this.direction_ID = direction_ID;
        this.block_ID = block_ID;
        this.shape_ID = shape_ID;

    }







    /**
     * returns a string to print out in GUI
     *
     * @return
     * @author Sofia Ricker
     */
    @Override
    public String toString() {
        return this.route_ID + "," + this.service_ID + "," + this.trip_ID + "," + this.trip_headsign + "," +
                this.direction_ID + "," + this.block_ID + "." + this.shape_ID;
    }


    /**
     * author: cece
     * gets the block associated with trip
     *
     * @return block_id
     */
    public long getBlock_ID() {
        return block_ID;
    }

    /**
     * author: cece
     * gets the direction of the travel for the trip
     *
     * @return direction_id
     */
    public int getDirection_ID() {
        return 0;
    }

    /**
     * author: cece
     * gets the route
     *
     * @return route_id
     */
    public String getRoute_ID() {
        return route_ID;
    }

    /**
     * author: cece
     * gets the set of dates when services are available
     *
     * @return dates
     */
    public String getService_ID() {
        return "null";
    }

    /**
     * author: cece
     * gets geospatial shape that describes
     * travel path for trip
     *
     * @return shape_id
     */
    public String getShape_ID() {
        return null;
    }

    /**
     * author: cece
     * gets visual text that identifies the trips destination to riders
     *
     * @return trip_headsign
     */
    public String getTrip_headsign() {
        return null;
    }

    /**
     * author: cece
     * gets the trip
     *
     * @return trip_id
     */
    public String getTrip_ID() {
        return trip_ID;
    }

    /**
     * author: cece
     * identifies the block associated with trip
     *
     * @param block_ID - number the block is on
     */
    public void setBlock_ID(int block_ID) {


    }

    /**
     * author: cece
     * identifies the direction of the travel for the trip
     *
     * @param direction_ID - number for the direction
     */
    public void setDirection_ID(int direction_ID) {

    }

    /**
     * author: cece
     * identifies route in the trp
     *
     * @param route_ID - number associated to route
     */
    public void setRoute_ID(int route_ID) {

    }

    /**
     * author: cece
     * identifies the set of dates when services are available
     *
     * @param service_ID - number of availability
     */
    public void setService_ID(String service_ID) {

    }

    /**
     * author: cece
     * identifies geospatial shape that describes
     * travel path for trip
     *
     * @param shape_ID - string that describes the vehicle path
     */
    public void setShape_ID(String shape_ID) {

    }

    /**
     * author: cece
     * identifies visual text that identifies the trips destination to riders
     *
     * @param trip_headsign - string associated to destination
     */
    public void setTrip_headsign(String trip_headsign) {

    }

    /**
     * author: cece
     * sets the trip id
     *
     * @param trip_ID - string associated to trip
     */
    public void setTrip_ID(String trip_ID) {

    }

    /**
     * author: cece
     * gets the average speed of trip
     *
     * @param startTime - departure
     * @param endTime   - arrival
     */
    public double getAverageSpeed(Time startTime, Time endTime) {
        return 0;
    }

    /**
     * author: cece
     * gets the distance between trips
     *
     * @return distance
     */
    public double getDistance() {
        return 0;
    }


}