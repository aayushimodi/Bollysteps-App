/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.ui.student;

import bollysteps.database.student.Student;
import bollysteps.ui.UI;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 *
 * @author aayus
 */
public class AddStudentDialog extends StudentDialog {

    public AddStudentDialog(UI ui) {
        super(ui);
        this.setTitle("Add a Student");
        this.showDialog();
    }

    @Override
    protected StudentInfoPanel createInfoPanel(UI ui) {
        return new StudentInfoPanel(ui, StudentInfoPanel.Usage.Create);
    }

    @Override
    protected boolean saveStudent() {
        Student s = this.getInfoPanel().createStudent(this);
        if (s == null) {
            return false;
        }
        try {
            this.ui.getDB().addStudent(s);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", ERROR_MESSAGE);
            return false;
        }
    }
}
