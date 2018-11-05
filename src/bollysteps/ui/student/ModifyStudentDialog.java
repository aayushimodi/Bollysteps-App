/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.ui.student;

import bollysteps.database.student.Student;
import bollysteps.ui.UI;
import javax.swing.JOptionPane;

/**
 *
 * @author aayus
 */

//version of StudentDialog specifically for modifying classes

public class ModifyStudentDialog extends StudentDialog {

    public ModifyStudentDialog(UI ui, Student s) {
        super(ui);
        this.setTitle("Modify Student");
        this.getInfoPanel().displayStudent(s);
        this.showDialog();
    }

      //sets up the ClassInfoPanel with the modify usage so the name JTextField will not be editable
    
    @Override
    protected StudentInfoPanel createInfoPanel(UI ui) {
        return new StudentInfoPanel(ui, StudentInfoPanel.Usage.Modify);
    }

     //if a student has been successfully created, this method tries to call the modifyStudent method of the Database
    
    @Override
    protected boolean saveStudent() {
         Student s = this.getInfoPanel().createStudent(this);
        if (s == null) {
            return false;
        }

        try {
            // try to save the student in the database
            this.ui.getDB().modifyStudent(s);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            return false;
        }
    }
}
