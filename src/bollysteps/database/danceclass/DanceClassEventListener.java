/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.database.danceclass;

/**
 *
 * @author aayus
 */

//interface for eventListener methods

public interface DanceClassEventListener {
    	void danceClassAdded(DanceClassEvent event);
	
	void danceClassRemoved(DanceClassEvent event);
	
	void danceClassModified(ModifiedDanceClassEvent event);
}
