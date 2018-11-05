/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.ui.danceclass;

import bollysteps.database.danceclass.DanceClass;
import bollysteps.ui.UI;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author aayus
 */

//JPanel with JTextFields and JLabels to create, modify, or view a class

public class ClassInfoPanel extends JPanel {

    private JTextField nameField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextField studentCountField;

    private JPanel basicInfoPanel;
    private JPanel iPanel;

    private Usage usage;

    private UI ui;

    public ClassInfoPanel(UI ui, ClassInfoPanel.Usage usage) {
        this.usage = usage;
        this.ui = ui;

        this.createComponents();
        this.layoutComponents();
    }

    private void createComponents() {
        this.createBasicInfoPanel();
    }

    private void layoutComponents() {
        this.setLayout(new BorderLayout());
        this.add(this.basicInfoPanel, BorderLayout.CENTER);
    }

    private void createBasicInfoPanel() {
        this.basicInfoPanel = new JPanel(new BorderLayout());
        this.basicInfoPanel.setBackground(Color.cyan);
        JLabel mTitle = new JLabel("Class Information");
        mTitle.setFont(ui.getTextFont());
        mTitle.setHorizontalAlignment(SwingConstants.CENTER);

        iPanel = new JPanel();

        Border border = iPanel.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        iPanel.setBorder(new CompoundBorder(border, margin));
        GridBagLayout gbLayout = new GridBagLayout();

        gbLayout.columnWidths = new int[]{86, 86, 0};
        gbLayout.rowHeights = new int[]{20, 20, 20, 20, 20, 0};
        gbLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};

        iPanel.setLayout(gbLayout);

        this.nameField = createLabelAndTextField("Name: ", 0, iPanel);
        if (this.usage != Usage.Create) {
            this.nameField.setEditable(false);
        }

        this.startTimeField = createLabelAndTextField("Start Time: ", 1, iPanel);
        this.endTimeField = createLabelAndTextField("End Time: ", 2, iPanel);

        this.basicInfoPanel.add(mTitle, BorderLayout.NORTH);
        this.basicInfoPanel.add(iPanel, BorderLayout.CENTER);
    }

    private JTextField createLabelAndTextField(String labelText, int yPos, Container containingPanel) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Times", Font.PLAIN, 15));

        GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
        gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
        gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);
        gridBagConstraintForLabel.gridx = 0;
        gridBagConstraintForLabel.gridy = yPos;
        containingPanel.add(label, gridBagConstraintForLabel);

        JTextField textField = new JTextField();
        textField.setFont(new Font("Times", Font.PLAIN, 15));
        textField.setEditable(this.usage != Usage.View);
        GridBagConstraints gridBagConstraintForTextField = new GridBagConstraints();
        gridBagConstraintForTextField.fill = GridBagConstraints.BOTH;
        gridBagConstraintForTextField.insets = new Insets(0, 0, 5, 0);
        gridBagConstraintForTextField.gridx = 1;
        gridBagConstraintForTextField.gridy = yPos;
        containingPanel.add(textField, gridBagConstraintForTextField);
        textField.setColumns(10);

        return textField;
    }

    public void displayClass(DanceClass dc) {
        this.nameField.setText(dc.getName());
        this.startTimeField.setText(dc.getStartTime());
        this.endTimeField.setText(dc.getEndTime());

    }

    public DanceClass createClass(Component owner) {

        if (!validateFields(owner)) {
            return null;

        } else {

            DanceClass dc = new DanceClass(this.nameField.getText().trim());

            dc.setStartTime(this.startTimeField.getText());
            dc.setEndTime(this.endTimeField.getText());

            return dc;
        }
    }

    //ensure that the class has a name 
    
    private boolean validateFields(Component owner) {
        if (this.nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(owner, "The class name must not be empty.");
            return false;
        }
        return true;
    }
    
    //emums used to distinguish the use of panel, and change fields from editable to noneditable to match use 

    public enum Usage {
        Create,
        View,
        Modify
    }
}
