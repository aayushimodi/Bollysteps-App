/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.database.student;

/**
 *
 * @author aayus
 */
public interface StudentEventListener {
    	void studentAdded(StudentEvent event);
	
	void studentRemoved(StudentEvent event);
	
	void studentModified(ModifiedStudentEvent event);
}
