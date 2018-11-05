/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.ui;

import bollysteps.database.Database;
import static bollysteps.ui.UI.FRAME_HEIGHT;
import static bollysteps.ui.UI.FRAME_WIDTH;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.*;
import javax.swing.*;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import javax.swing.border.*;

/**
 *
 * @author aayus
 */
//JPanel used for the Home Tab
public class HomePanel extends JPanel {

    private UI ui;
    private JPanel buttonPanel;
    private JLabel title;

    public HomePanel(UI ui) {
        this.ui = ui;
        this.setBackground(Color.cyan);
        this.setLayout(new BorderLayout());
        this.createButtonPanel();
        this.add(this.buttonPanel, BorderLayout.SOUTH);
        title = new JLabel("Bollysteps Dance");
        title.setHorizontalAlignment(JLabel.CENTER);

        JLabel bg = new JLabel();
        ImageIcon icon = new ImageIcon("DSC_4424.jpg");
        bg.setIcon(icon);
        bg.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.add(bg);

        title.setFont(ui.getTitleFont());
        this.add(title, BorderLayout.NORTH);
    }

    //setting up buttons and linking them to either other tabs or JDialogs
    private void createButtonPanel() {
        this.buttonPanel = new JPanel();
        GridLayout grid = new GridLayout();
        grid.setHgap(20);
        this.buttonPanel.setLayout(grid);

        JButton classesButton = new JButton("Classes");
        classesButton.setFont(ui.getTextFont());
        classesButton.setBorder(new LineBorder(Color.orange, 12));
        this.buttonPanel.add(classesButton);

        classesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.gotoTab(1);
            }
        });

        JButton studentsButton = new JButton("Students");
        studentsButton.setFont(ui.getTextFont());
        studentsButton.setBorder(new LineBorder(Color.pink, 12));
        this.buttonPanel.add(studentsButton);

        studentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ui.gotoTab(2);
            }
        });

        JButton deleteAllButton = new JButton("Delete All");
        deleteAllButton.setFont(ui.getTextFont());
        deleteAllButton.setBorder(new LineBorder(Color.GREEN, 12));
        this.buttonPanel.add(deleteAllButton);

        deleteAllButton.addActionListener(new ActionListener() { //confirming that the user really wants to delete all data
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(
                        ui.getFrame(),
                        "Are you sure you want to delete all stored dance classes and students?\nThe app will close once all information is deleted.",
                        "Confirm Delete All",
                        YES_NO_OPTION);

                if (confirmation == YES_OPTION) {
                    ui.getDB().deleteAll();

                    try {
                        ui.getDB().saveTo(Database.dbFileName);
                    } catch (IOException ex) {
                        Logger.getLogger("DB").log(Level.SEVERE, null, ex);
                    }
                    ui.getFrame().dispose();
                }
            }
        });
    }
}
