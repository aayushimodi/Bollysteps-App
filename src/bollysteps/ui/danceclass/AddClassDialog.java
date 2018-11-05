/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.ui.danceclass;

import bollysteps.database.danceclass.DanceClass;
import bollysteps.ui.UI;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 *
 * @author aayus
 */

//version of ClassDialog specifically for adding classes

public class AddClassDialog extends ClassDialog {

    public AddClassDialog(UI ui) {
        super(ui);
        this.setTitle("Add a Class");
        this.showDialog();
    }

    //sets up the ClassInfoPanel with the create usage so all the JTextFields will be editable
    
    @Override
    protected ClassInfoPanel createInfoPanel(UI ui) {
        return new ClassInfoPanel(ui, ClassInfoPanel.Usage.Create);
    }

    //if a class has been successfully created, this method tries to call the addDanceClass method of the Database
    
    @Override
    protected boolean saveClass() {
        DanceClass dc = this.getInfoPanel().createClass(this);
        if (dc == null) {
            return false;
        }
       
        try {
            this.ui.getDB().addDanceClass(dc);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", ERROR_MESSAGE);
            return false;
        }
    }
}
