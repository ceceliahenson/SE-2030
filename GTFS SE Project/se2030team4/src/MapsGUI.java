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

import java.util.Collection;

/**
 * @author Ethan Jeffers
 * @version 1.0
 * @created 05-Oct-2022 10:08:55 AM
 */
public class MapsGUI implements Observer {

	public GTFSSubject m_GTFSSubject;
	public Controller m_Controller;

	public MapsGUI(GTFSSubject gtfsSubject){
		gtfsSubject.attach(this);
	}

	/**
	 * Allows user to click and drag to change the location on a stop
	 * UNIMPLEMENTED
	 */
	public void clickAndDrag(){

	}

	/**
	 * Allows user to click on a given stop
	 * UNIMPLEMENTED
	 */
	public void clickOnStop(){

	}


	//not implemented
	@Override
	public void update(Collection<Object> s) {

	}
}
