/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.ui.danceclass;

import bollysteps.database.danceclass.DanceClass;
import bollysteps.database.danceclass.DanceClassEvent;
import bollysteps.database.danceclass.DanceClassEventListener;
import bollysteps.database.Database;
import bollysteps.database.danceclass.ModifiedDanceClassEvent;
import bollysteps.ui.UI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import javax.swing.border.LineBorder;

/**
 *
 * @author aayus
 */

//main JPanel that is used for the classes tab 

public class DanceClassesMainPanel extends JPanel implements DanceClassEventListener {

    private final UI ui;

    private JTabbedPane tabbedPanel;
    private JPanel buttonPanel;
    private ArrayList<DanceClass> classes;

    public DanceClassesMainPanel(UI ui) {
        this.ui = ui;
        Database db = ui.getDB();
        db.addEventListener(this);

        this.classes = new ArrayList<DanceClass>(db.getAllClasses());

        this.createComponents();
        this.layoutComponents();
    }

    //methods to set up the JPanel's components and design 
    
    private void createComponents() {
        this.createButtonPanel();
        this.createTabbedPanel();
    }

    private void layoutComponents() {
        this.setLayout(new BorderLayout());
        this.add(this.buttonPanel, BorderLayout.SOUTH);
        this.add(this.tabbedPanel, BorderLayout.CENTER);
        this.tabbedPanel.getModel();
    }

    //method to set up buttons, including action listeners to call certain code when button is clicked 
    private void createButtonPanel() {
        this.buttonPanel = new JPanel();
        GridLayout grid = new GridLayout(1,2);
        grid.setHgap(20);
        this.buttonPanel.setLayout(grid);

        JButton addClassButton = new JButton("Add Class");
        addClassButton.setFont(this.ui.getTextFont());
        addClassButton.setBorder(new LineBorder(Color.orange, 12));
        
        addClassButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new AddClassDialog(ui);
            }
        });

        this.buttonPanel.add(addClassButton);

        JButton removeClassButton = new JButton("Remove Class");
        removeClassButton.setFont(this.ui.getTextFont());
        removeClassButton.setBorder(new LineBorder(Color.pink, 12));

        removeClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tabbedPanel.getSelectedIndex();
                String className = tabbedPanel.getTitleAt(index);
                int confirmation = JOptionPane.showConfirmDialog(
                        ui.getFrame(),
                        "Are you sure you want to delete this dance class?\nYou will not be able to recover the class after deletion.",
                        "Confirm Class Delete",
                        YES_NO_OPTION);

                if (confirmation == YES_OPTION) {
                    ui.getDB().deleteDanceClass(className);
                }

            }
        });
        this.buttonPanel.add(removeClassButton);

        JButton modifyClassButton = new JButton("Modify Class");
        modifyClassButton.setFont(this.ui.getTextFont());
        modifyClassButton.setBorder(new LineBorder(Color.green, 12));

        modifyClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DanceClass dc = classes.get(tabbedPanel.getSelectedIndex());
                new ModifyClassDialog(ui, dc);
            }

        });
        this.buttonPanel.add(modifyClassButton);
    }

    private void createTabbedPanel() {
        this.tabbedPanel = new JTabbedPane();
        this.tabbedPanel.setFont(this.ui.getTextFont());
        this.tabbedPanel.setTabPlacement(JTabbedPane.LEFT);
        this.addClassPanels();
    }

    private void addClassPanels() {
        for (DanceClass dc : this.classes) {
            JPanel danceClassPanel = new DanceClassTab(this.ui, dc);
            this.tabbedPanel.add(dc.getName(), danceClassPanel);
        }
    }
    
    //implemented methods from DanceClassEventListener

    @Override
    public void danceClassAdded(DanceClassEvent event) {
        DanceClass dc = event.getDanceClass();
        JPanel danceClassPanel = new DanceClassTab(this.ui, dc);
        this.tabbedPanel.add(dc.getName(), danceClassPanel);
        this.classes.add(dc);
    }

    @Override
    public void danceClassRemoved(DanceClassEvent event) {
        DanceClass dc = event.getDanceClass();
        int index = this.classes.indexOf(dc);
        this.tabbedPanel.remove(index);
        this.classes.remove(index);
    }

    @Override
    public void danceClassModified(ModifiedDanceClassEvent event) {
        int index = this.classes.indexOf(event.getDanceClass());
        this.classes.remove(index);
        this.classes.add(index, event.getNewDanceClass());
    }
}
