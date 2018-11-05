/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.ui.student;

import bollysteps.database.student.Student;
import bollysteps.ui.UI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 *
 * @author aayus
 */

//main JPanel that is used for the classes tab 

public class StudentsPanel extends JPanel {

    private final UI ui;

    private JScrollPane tablePanel;
    private JPanel buttonPanel;

    private JTable table;
    private StudentsTableModel tableModel;

    private StudentInfoPanel infoPanel;

    public StudentsPanel(UI ui) {
        this.ui = ui;
        this.createComponents();
        this.layoutComponents();
    }
    
     //methods to set up the JPanel's components and design 

    private void createComponents() {
        this.createButtonPanel();
        this.createTablePanel();
        this.createInfoPanel();
    }

    private void layoutComponents() {
        this.setLayout(new BorderLayout());

        JPanel bottomPanel = new JPanel(new BorderLayout());

        bottomPanel.add(this.buttonPanel, BorderLayout.SOUTH);
        bottomPanel.add(this.infoPanel, BorderLayout.CENTER);

        this.add(this.tablePanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }
    
//method to set up buttons, including action listeners to call certain code when button is clicked 
    
    private void createButtonPanel() {
        this.buttonPanel = new JPanel();
        GridLayout grid = new GridLayout(1, 2);
        grid.setHgap(20);
        this.buttonPanel.setLayout(grid);

        JButton addStudentButton = new JButton("Add Student");
        addStudentButton.setFont(this.ui.getTextFont());
        addStudentButton.setBorder(new LineBorder(Color.orange, 12));

        addStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddStudentDialog(ui);
            }
        });

        this.buttonPanel.add(addStudentButton);

        JButton modifyStudentButton = new JButton("Modify Student");
        modifyStudentButton.setFont(this.ui.getTextFont());
        modifyStudentButton.setBorder(new LineBorder(Color.pink, 12));

        modifyStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                System.out.println(row);
                if (row != -1) {
                    Student s = tableModel.getStudent(row);
                    StudentDialog d = new ModifyStudentDialog(ui, s);
                    s = tableModel.getStudent(row);
                    infoPanel.displayStudent(s);
                }
            }
        });

        this.buttonPanel.add(modifyStudentButton);

        JButton removeStudentButton = new JButton("Remove Student");
        removeStudentButton.setFont(this.ui.getTextFont());
        removeStudentButton.setBorder(new LineBorder(Color.green, 12));

        removeStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    String studentName = (String) tableModel.getValueAt(row, 0);

                    int confirmation = JOptionPane.showConfirmDialog(
                            ui.getFrame(),
                            "Are you sure you want to delete this student?\nYou will not be able to recover the student after deletion.",
                            "Confirm Student Delete",
                            YES_NO_OPTION);

                    if (confirmation == YES_OPTION) {
                        ui.getDB().deleteStudent(studentName);
                    }
                } else {
                    JOptionPane.showMessageDialog(ui.getFrame(), "You must select a student to delete them.", "Error", ERROR_MESSAGE);
                }
            }
        });

        this.buttonPanel.add(removeStudentButton);
    }
    
    //setting up JTable

    private void createTablePanel() {
        this.tableModel = new StudentsTableModel(this.ui.getDB());
        this.table = new JTable(this.tableModel);
        this.tablePanel = new JScrollPane(this.table);
        this.table.setFillsViewportHeight(true);
        this.table.getTableHeader().setFont(this.ui.getTitleFont());
        this.table.setFont(this.ui.getTextFont());
        this.table.setRowHeight(table.getRowHeight() * 4);
        this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    Student s = tableModel.getStudent(row);
                    infoPanel.displayStudent(s);
                }
            }

        });
    }

    private void createInfoPanel() {
        this.infoPanel = new StudentInfoPanel(this.ui, StudentInfoPanel.Usage.View);
    }

}
