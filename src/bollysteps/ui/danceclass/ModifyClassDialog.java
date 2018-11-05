/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.ui.danceclass;

import bollysteps.database.danceclass.DanceClass;
import bollysteps.ui.UI;
import javax.swing.JOptionPane;

/**
 *
 * @author aayus
 */

//version of ClassDialog specifically for modifying classes

public class ModifyClassDialog extends ClassDialog {

    public ModifyClassDialog(UI ui, DanceClass dc) {
        super(ui);
        this.setTitle("Modify Class");
        this.getInfoPanel().displayClass(dc);
        this.showDialog();
    }

    //sets up the ClassInfoPanel with the modify usage so the name JTextField won't be editable
    @Override
    protected ClassInfoPanel createInfoPanel(UI ui) {
        return new ClassInfoPanel(ui, ClassInfoPanel.Usage.Modify);
    }
    
     //if a class has been successfully created, this method tries to call the modifyDanceClass method of the Database

    @Override
    protected boolean saveClass() {
         DanceClass dc = this.getInfoPanel().createClass(this);
        if (dc == null) {
            return false;
        }

        try {
            this.ui.getDB().modifyDanceClass(dc);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            return false;
        }
    }
}
