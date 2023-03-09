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

import java.awt.*;
import java.util.Collection;

/**
 * @author Cecelia Henson
 * @version 1.0
 * @created 05-Oct-2022 10:08:58 AM
 */
public class Route {

	private String agency_ID;
	private String route_color;
	private String route_desc;
	private String route_ID;
	private String route_long_name;
	private String route_short_name;
	private String route_text_color;
	private int route_type;
	private String route_url;




	/**
	 * route method
	 * @param agency_ID
	 * @param route_color
	 * @param route_desc
	 * @param route_ID
	 * @param route_long_name
	 * @param route_text_color
	 * @param route_type
	 * @param route_url
	 * @author Sofia
	 */
	public Route(String route_ID, String agency_ID, String route_short_name, String route_long_name, String route_desc,
				 int route_type, String route_url, String route_color, String route_text_color){
		this.agency_ID = agency_ID;
		this.route_color = route_color;
		this.route_desc = route_desc;
		this.route_ID = route_ID;
		this.route_long_name = route_long_name;
		this.route_short_name = route_short_name;
		this.route_text_color = route_text_color;
		this.route_type = route_type;
		this.route_url = route_url;
	}

	public boolean validateRouteImport(Collection<String> lines){
		return false;
	}



	/**
	 * author: cece
	 *get the route id
	 * @return route_id
	 */
	public String getRoute_ID(){
		return route_ID;
	}

	/**
	 * author: cece
	 * gets the agency id
	 * @return agency_id
	 */
	public String getAgency_ID(){
		return null;
	}

	/**
	 * author: cece
	 * gets the color associated to the route
	 * @return color
	 */
	public String getRoute_color() {
		return route_color;
	}

	/**
	 * author: cece
	 * gets the route description
	 * @return route description
	 */
	public String getRoute_desc() {
		return null;
	}

	/**
	 * author: cece
	 * sets the agency id
	 * @param agency_ID - specific route
	 */
	public void setAgency_ID(String agency_ID) {

	}

	/**
	 * author: cece
	 * gets the route text color
	 * @return color
	 */
	public Color getRoute_text_color() {
		return null;
	}

	/**
	 * author: cece
	 * gets the routes short name
	 * @return route short name
	 */
	public String getRoute_short_name() {
		return null;
	}

	/**
	 * author: cece
	 * initializes the route color
	 * @param route_color - color of the route
	 */
	public void setRoute_color(Color route_color) {

	}

	/**
	 * author: cece
	 * gets the route type
	 * @return route type
	 */
	public int getRoute_type() {
		return 0;
	}

	/**
	 * author: cece
	 * initialised the route id
	 * @param route_ID route
	 */
	public void setRoute_ID(int route_ID) {

	}

	/**
	 * author: cece
	 * gets the routes longer name
	 * @return longer name
	 */
	public String getRoute_long_name() {
		return null;
	}

	/**
	 * author: cece
	 * gets the routes url
	 * @return url
	 */
	public String getRoute_url() {
		return null;
	}

	/**
	 * author: cece
	 * identifies the  route description
	 * @param route_desc route description
	 */
	public void setRoute_desc(String route_desc) {

	}

	/**
	 * author: cece
	 * initialized the routes longer name
	 * @param route_long_name routes longer name
	 */
	public void setRoute_long_name(String route_long_name) {

	}

	/**
	 * author: cece
	 * initializes the routes shorter name
	 * @param route_short_name routes shorter name
	 */
	public void setRoute_short_name(int route_short_name) {

	}

	/**
	 * author: cece
	 * sets the routes color
	 * @param route_text_color route color
	 */
	public void setRoute_text_color(Color route_text_color) {

	}

	/**
	 * author: cece
	 * sets the route type
	 * @param route_type type of route
	 */
	public void setRoute_type(int route_type) {

	}

	/**
	 * author: cece
	 * sets the route url
	 * @param route_url url of route
	 */
	public void setRoute_url(String route_url) {

	}

	/**
	 * author: cece
	 * changes the routes location
	 * @param newLatitude new latitude variable
	 * @param newLongitude new longitude variabl
	 */
	public void changeRouteLocation(double newLatitude, double newLongitude){

	}

	/**
	 * author: cece
	 * plots the buses location in the current time
	 * @param currentTime - current time
	 */
	public double plotBusLocation(String currentTime){
		return 0;
	}

	@Override
	public String toString(){
		return this.route_ID + "," + this.agency_ID + "," + this.route_short_name + "," +
				this.route_long_name + "," + this.route_desc + "," + this.route_type + "," +
				this.route_color + "," + this.route_text_color;
	}


}