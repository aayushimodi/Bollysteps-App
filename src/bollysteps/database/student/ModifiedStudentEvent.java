/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.database.student;

import bollysteps.database.Database;

/**
 *
 * @author aayus
 */
//Version of studentEvent but with two students involved, the existing student and the new one 

public class ModifiedStudentEvent extends StudentEvent {
    private Student newStudent;
    
    public ModifiedStudentEvent(Database source, Student existingStudent, Student newStudent) {
        super(source, existingStudent);
        this.newStudent = newStudent;
    }
    
    public Student getNewStudent() {
        return newStudent;
    }
    
}
