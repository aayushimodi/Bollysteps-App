/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.database.student;

import bollysteps.database.Database;
import java.util.EventObject;

/**
 *
 * @author aayus
 */

//Event that is fired for a specific student when changes are made


public class StudentEvent extends EventObject {
    private final Student student;
	
	public StudentEvent(Database source, Student student) {
		super(source);
		this.student = student;
	}
	
	public Database getDatabase() {
		return (Database) this.getSource();
	}

	public Student getStudent() {
		return this.student;
	}
}
