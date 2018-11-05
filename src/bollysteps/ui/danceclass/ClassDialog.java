/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.ui.danceclass;

import bollysteps.database.*;
import bollysteps.ui.UI;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author aayus
 */

//JDialog that combines ClassInfoPanel with buttons 
//methods to layout design of this JDialog

public abstract class ClassDialog extends JDialog {
    
    protected UI ui;
    
    private JPanel buttonPanel;
    private ClassInfoPanel infoPanel;
    private JButton saveButton;
    private JButton cancelButton;
    
    public ClassDialog(UI ui) {
       super(ui.getFrame(), true);
       
        this.ui = ui;
        
        this.createComponents();
        this.layoutComponents();
    }
    
    private void createComponents() {
        this.createButtonPanel();
        this.infoPanel = this.createInfoPanel(this.ui);
    }
    
    private void createButtonPanel() {
        
        this.buttonPanel = new JPanel(new FlowLayout(20));
        this.buttonPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
       
        this.saveButton = new JButton("Save");
        this.saveButton.setFont(ui.getTextFont());
        this.buttonPanel.add(this.saveButton);
        
        this.cancelButton = new JButton("Cancel");
        this.cancelButton.setFont(ui.getTextFont());
        this.buttonPanel.add(this.cancelButton);
        
        this.addButtonActionListeners();
    }
    
    private void addButtonActionListeners() {
        this.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (saveClass()) {
                    dispose();
                }
            }
        });
    }
    
    protected abstract boolean saveClass();
    
    protected abstract ClassInfoPanel createInfoPanel(UI ui);
        
    private void layoutComponents() {
       
        Container contentPane = this.getContentPane();
       
        int width = (int) (0.7 * UI.FRAME_WIDTH);
        int height = (int) (0.7 * UI.FRAME_HEIGHT);
        this.setSize(width, height);
        this.setLocationRelativeTo(this.getParent());
        
        contentPane.setLayout(new BorderLayout());
        this.add(this.buttonPanel, BorderLayout.SOUTH);
        this.add(this.infoPanel, BorderLayout.CENTER);
    }
    
    protected void showDialog() {
        this.setVisible(true);
    }
    
    public ClassInfoPanel getInfoPanel(){
        return this.infoPanel;
    }
}
