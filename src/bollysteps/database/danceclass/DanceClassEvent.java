/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.database.danceclass;

import bollysteps.database.Database;
import java.util.EventObject;

/**
 *
 * @author aayus
 */

//Event that is fired for a specific class when changes are made

public class DanceClassEvent extends EventObject {

    private final DanceClass danceClass; 
    
    public DanceClassEvent(Database source, DanceClass danceClass) {
        super(source);

        this.danceClass = danceClass;
    }

    public Database getDatabase() {
        return (Database) this.getSource();
    }

    public DanceClass getDanceClass() {
        return this.danceClass;
    }
}
