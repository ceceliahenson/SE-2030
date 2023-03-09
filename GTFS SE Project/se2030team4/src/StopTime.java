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

/**
 * Objects that represents a Stop Time and holds those attributes associated with a stop time
 */
public class StopTime {
	private String arrival_time;
	private String departure_time;
	private int drop_off_type;
	private int pickup_type;
	private String stop_headsign;
	private String stop_id;
	private int stop_seq;
	private String trip_id;

	/**
	 * Uses the information from the GTFS file to set the attributes a stop time
	 * @param arrival_time - Arrival time at a specific stop for a specific trip on a route.
	 * @param departure_time - Departure time at a specific stop for a specific trip on a route.
	 * @param drop_off_type - Indicates drop off method. Valid options are 0 or empty - Regularly scheduled drop off, 1 -No drop off available, 2 - Must phone agency to arrange drop off, 3 - Must coordinate with driver to arrange drop off.
	 * @param pickup_type-Indicates pickup method. Valid options: 0 or empty - Regularly scheduled pickup, 1 - No pickup available, 2 - Must phone agency to arrange pickup, 3 - Must coordinate with driver to arrange pickup.
	 * @param stop_headsign - Text that appears on signage identifying the trip's destination to riders.
	 * @param stop_id - Identifies the serviced stop
	 * @param stop_seq - Order of stops for a particular trip.
	 * @param trip_id -Identifies a trip.
	 */
	public StopTime(String trip_id,String arrival_time,String departure_time,String stop_id,int stop_seq,String stop_headsign,int pickup_type,int drop_off_type) {
		this.arrival_time = arrival_time;
		this.departure_time = departure_time;
		this.drop_off_type = drop_off_type;
		this.pickup_type = pickup_type;
		this.stop_headsign = stop_headsign;
		this.stop_id = stop_id;
		this.stop_seq = stop_seq;
		this.trip_id = trip_id;
	}

	/**
	 * returns a string to print out in GUI
	 * @author Sofia
	 * @return
	 */
	@Override
	public String toString(){
		return this.trip_id + "," + this.arrival_time + "," + this.departure_time + "," + this.stop_id +
				"," + this.stop_seq + "," + this.stop_headsign + "," + this.pickup_type + "," + this.drop_off_type;
	}


	public String getArrival_time() {
		return arrival_time;
	}

	public String getDeparture_time() {
		return departure_time;
	}

	public int getDrop_off_type() {
		return drop_off_type;
	}

	public int getPickup_type() {
		return pickup_type;
	}

	public String getStop_headsign() {
		return stop_headsign;
	}

	public String getStop_id() {
		return stop_id;
	}

	public int getStop_seq() {
		return stop_seq;
	}

	public String getTrip_id() {
		return trip_id;
	}


	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}

	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}

	public void setDrop_off_type(int drop_off_type) {
		this.drop_off_type = drop_off_type;
	}

	public void setPickup_type(int pickup_type) {
		this.pickup_type = pickup_type;
	}

	public void setStop_headsign(String stop_headsign) {
		this.stop_headsign = stop_headsign;
	}

	public void setStop_id(String stop_id) {
		this.stop_id = stop_id;
	}

	public void setStop_seq(int stop_seq) {
		this.stop_seq = stop_seq;
	}

	public void setTrip_id(String trip_id) {
		this.trip_id = trip_id;
	}



}