/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.ui.danceclass;

import bollysteps.database.danceclass.DanceClass;
import bollysteps.database.danceclass.DanceClassEventListener;
import bollysteps.database.danceclass.DanceClassEvent;
import bollysteps.database.danceclass.ModifiedDanceClassEvent;
import bollysteps.database.*;
import bollysteps.ui.student.StudentsTableModel;
import bollysteps.ui.UI;
import java.awt.BorderLayout;
import javax.swing.*;

/**
 *
 * @author aayus
 */

//JPanel to show information about a single class 
//methods for layout and design 

public class DanceClassTab extends JPanel implements DanceClassEventListener {
    
    private final UI ui;
    
    private DanceClass danceClass;
    private StudentsTableModel studentsTableModel;
    
    private ClassInfoPanel infoPanel;
    private JScrollPane studentsTablePanel;
    private JTable studentsTable;
    
    public DanceClassTab(UI ui, DanceClass danceClass) {
        this.ui = ui;
        this.ui.getDB().addEventListener(this);
        this.danceClass = danceClass;
        this.studentsTableModel = new StudentsTableModel(
                this.ui.getDB(), 
                this.danceClass.getName());
        
        this.createComponents();
        this.layoutComponents();
    }

    private void createComponents() {
        
        this.createTablePanel();
        this.createInfoPanel();
    }

    private void layoutComponents() {
        this.setLayout(new BorderLayout());
        
        this.add(this.infoPanel, BorderLayout.NORTH);
        this.add(this.studentsTablePanel, BorderLayout.CENTER);
    }

    private void createTablePanel() {
        this.studentsTable = new JTable(this.studentsTableModel);
        this.studentsTablePanel = new JScrollPane(this.studentsTable);
        this.studentsTable.setFillsViewportHeight(true);
        this.studentsTable.getTableHeader().setFont(this.ui.getTitleFont());
        this.studentsTable.setFont(this.ui.getTextFont());
        this.studentsTable.setRowHeight(this.studentsTable.getRowHeight() * 4);
    }

    private void createInfoPanel() {
        this.infoPanel = new ClassInfoPanel(this.ui, ClassInfoPanel.Usage.View);
        this.infoPanel.displayClass(this.danceClass);
    }

    //method that would be called if a class was modified, displays the new modified information in place of old info
    @Override
    public void danceClassModified(ModifiedDanceClassEvent event) {
        if (event.getDanceClass() == this.danceClass) {
            this.danceClass = event.getNewDanceClass();
            this.infoPanel.displayClass(this.danceClass);
        }
    }

    @Override
    public void danceClassAdded(DanceClassEvent event) {
       
    }

    @Override
    public void danceClassRemoved(DanceClassEvent event) {
        
    }
}