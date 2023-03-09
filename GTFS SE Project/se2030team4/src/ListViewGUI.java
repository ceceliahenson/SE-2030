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

import javafx.beans.Observable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Ethan Jeffers
 * @version 1.0
 * @created 05-Oct-2022 10:08:54 AM
 */
public class ListViewGUI implements Observer {

	public GTFSSubject gtfsSubject;
	public Controller controller;
	private Label routeList;

	public ListViewGUI(GTFSSubject gtfsSubject, Controller controller){
		this.controller = controller;
		this.gtfsSubject = gtfsSubject;
		routeList = new Label();
		gtfsSubject.attach(this);
	}

	public GTFSSubject getGTFS(){
		return gtfsSubject;

	}



	/**
	 * Will allow user to search for routes
	 * UNIMPLEMENTED
	 */
	public void onSearchRoutes(){

	}

	/**
	 * Will allow user to search for stops
	 * UNIMPLEMENTED
	 */
	public void onSearchStops(){

	}

	/**
	 * Will allow user to search for trips using a route
	 * UNIMPLEMENTED
	 */
	public void onSearchTripsByRoute(){

	}

	/**
	 * Will allow user to search for trips using a stop
	 * UNIMPLEMENTED
	 */
	public void onSearchTripsByStop(){

	}

	/**
	 * Pushes items passed in into the controller text field
	 * @author Ethan Jeffers
	 * @param args
	 */
	@Override
	public void update(Collection<Object> args) {
		controller.text.setText("");
		for (Object s : args){
			controller.text.setText(controller.text.getText() + s.toString() + "\n");
		}
	}
}
