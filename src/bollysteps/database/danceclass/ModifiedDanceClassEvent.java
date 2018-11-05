/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.database.danceclass;

import bollysteps.database.Database;

/**
 *
 * @author aayus
 */

//Version of danceClassEvent but with two dance classes involved, the existing class and the new one 

public class ModifiedDanceClassEvent extends DanceClassEvent {

    private DanceClass newDC; 
    
    public ModifiedDanceClassEvent(Database source, DanceClass existingDC, DanceClass newDC) {
        super(source, existingDC);

        this.newDC = newDC;
    }

    public DanceClass getNewDanceClass() {
        return this.newDC;
    }
}
