/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.ui.student;

import bollysteps.database.danceclass.DanceClass;
import bollysteps.database.student.Student;
import bollysteps.ui.UI;
import java.awt.*;
import java.util.Collection;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author aayus
 */

//JPanel with JTextFields and JLabels to create, modify, or view a student

public class StudentInfoPanel extends JPanel {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField ageField;
    private JTextField classField;
    private JTextField parentNameField;
    private JTextField parentEmailField;
    private JTextField phoneNumField;
    private JComboBox<String> classChooser;
    private JTextField neckField;
    private JTextField bustField;
    private JTextField waistField;
    private JTextField abdomenField;
    private JTextField hipsField;
    private JTextField backField;
    private JTextField bicepsField;
    private JTextField forearmField;

    private JPanel basicInfoPanel;
    private JPanel measurementsPanel;
    private JPanel iPanel;

    private Usage usage;

    private UI ui;

    public StudentInfoPanel(UI ui, StudentInfoPanel.Usage usage) {
        this.usage = usage;
        this.ui = ui;

        this.createComponents();
        this.layoutComponents();
    }

    private void createComponents() {
        this.createBasicInfoPanel();
        this.createMeasurementsPanel();
    }

    private void layoutComponents() {

        if (this.usage != Usage.View) {
            this.setLayout(new BorderLayout());
            this.add(this.basicInfoPanel, BorderLayout.NORTH);

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(this.measurementsPanel, BorderLayout.NORTH);
            this.add(panel, BorderLayout.CENTER);

        } else {
            this.setLayout(new BorderLayout());
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(this.basicInfoPanel, BorderLayout.NORTH);
            this.add(panel, BorderLayout.CENTER);
            this.add(this.measurementsPanel, BorderLayout.EAST);
        }
    }

    private void createBasicInfoPanel() {
        this.basicInfoPanel = new JPanel(new BorderLayout());
        this.basicInfoPanel.setBackground(Color.CYAN);
        JLabel mTitle = new JLabel("Student Information");
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

        this.emailField = createLabelAndTextField("Email: ", 1, iPanel);
        this.ageField = createLabelAndTextField("Age: ", 2, iPanel);
        this.phoneNumField = createLabelAndTextField("Phone Number: ", 3, iPanel);
        this.parentNameField = createLabelAndTextField("Parent Name: ", 4, iPanel);
        this.parentEmailField = createLabelAndTextField("Parent Email: ", 5, iPanel);
        
        ageField.setName("age field");

        if (this.usage == Usage.View) {
            this.classField = createLabelAndTextField("Class Name: ", 6, iPanel);
        } else {
            this.createClassChooser();
            createLabelAndLayoutComponent("Choose Class:", 6, iPanel, this.classChooser);
        }

        this.basicInfoPanel.add(mTitle, BorderLayout.NORTH);
        this.basicInfoPanel.add(iPanel, BorderLayout.CENTER);
    }

    private void createMeasurementsPanel() {
        this.measurementsPanel = new JPanel(new BorderLayout());
        this.measurementsPanel.setBackground(Color.cyan);
        JLabel mTitle = new JLabel("Measurements");
        mTitle.setFont(ui.getTextFont());
        mTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel mPanel = new JPanel();

        Border border = mPanel.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        mPanel.setBorder(new CompoundBorder(border, margin));
        GridBagLayout gbLayout = new GridBagLayout();

        gbLayout.columnWidths = new int[]{86, 86, 0};
        gbLayout.rowHeights = new int[]{20, 20, 20, 20, 20, 0};
        gbLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};

        mPanel.setLayout(gbLayout);

        this.neckField = createLabelAndTextField("Neck: ", 1, mPanel);
        this.bustField = createLabelAndTextField("Bust: ", 2, mPanel);
        this.waistField = createLabelAndTextField("Waist: ", 3, mPanel);
        this.abdomenField = createLabelAndTextField("Abdomen: ", 4, mPanel);
        this.hipsField = createLabelAndTextField("Hips: ", 5, mPanel);
        this.backField = createLabelAndTextField("Back: ", 6, mPanel);
        this.bicepsField = createLabelAndTextField("Biceps: ", 7, mPanel);
        this.forearmField = createLabelAndTextField("Forearm: ", 8, mPanel);
        
        neckField.setName("neck field");
        bustField.setName("bust field");
        waistField.setName("waist field");
        abdomenField.setName("abdomen field");
        hipsField.setName("hips field");
        backField.setName("back field");
        bicepsField.setName("biceps field");
        forearmField.setName("forearm field");
        

        this.measurementsPanel.add(mTitle, BorderLayout.NORTH);
        this.measurementsPanel.add(mPanel, BorderLayout.CENTER);
    }

    private void createClassChooser() {

        Collection<DanceClass> classes = this.ui.getDB().getAllClasses();
        String[] classNames = new String[classes.size() + 1];
        classNames[0] = "";
        int i = 1;
        for (DanceClass c : classes) {
            classNames[i++] = c.getName();
        }

        this.classChooser = new JComboBox<>(classNames);
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

    private void createLabelAndLayoutComponent(String labelText, int yPos, Container containingPanel, Component component) {
        JLabel label = new JLabel(labelText);
        GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
        gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
        gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);
        gridBagConstraintForLabel.gridx = 0;
        gridBagConstraintForLabel.gridy = yPos;
        containingPanel.add(label, gridBagConstraintForLabel);

        GridBagConstraints gridBagConstraintForComponent = new GridBagConstraints();
        gridBagConstraintForComponent.fill = GridBagConstraints.BOTH;
        gridBagConstraintForComponent.insets = new Insets(0, 0, 5, 0);
        gridBagConstraintForComponent.gridx = 1;
        gridBagConstraintForComponent.gridy = yPos;
        containingPanel.add(component, gridBagConstraintForComponent);
    }

    public void displayStudent(Student s) {
        this.nameField.setText(s.getName());
        this.emailField.setText(s.getEmail());
        this.ageField.setText("" + s.getAge());
        this.parentNameField.setText(s.getParentName());
        this.neckField.setText("" + s.getNeck());
        this.bustField.setText("" + s.getBust());
        this.waistField.setText("" + s.getWaist());
        this.abdomenField.setText("" + s.getAbdomen());
        this.hipsField.setText("" + s.getHips());
        this.backField.setText("" + s.getBack());
        this.bicepsField.setText("" + s.getBiceps());
        this.forearmField.setText("" + s.getForearm());

        if (this.usage == Usage.View) {
            this.classField.setText(s.getClassName());
        } else {
            String className = s.getClassName();
            if (className != null) {
                this.classChooser.setSelectedItem(s.getClassName());
            }
        }
        this.parentEmailField.setText(s.getParentEmail());
        this.phoneNumField.setText(s.getPhoneNum());

    }

    public Student createStudent(Component owner) {

        if (!validateFields(owner)) {
            return null;

        } else {

            Student s = new Student(this.nameField.getText().trim());

            s.setEmail(this.emailField.getText());
            s.setParentName(this.parentNameField.getText());
            s.setParentEmail(this.parentEmailField.getText());
            s.setPhoneNum(this.phoneNumField.getText());

            if (!neckField.getText().isEmpty()) {
                s.setNeck(Double.parseDouble(this.neckField.getText()));
            }

            if (!bustField.getText().isEmpty()) {
                s.setBust(Double.parseDouble(this.bustField.getText()));
            }

            if (!waistField.getText().isEmpty()) {
                s.setWaist(Double.parseDouble(this.waistField.getText()));
            }

            if (!abdomenField.getText().isEmpty()) {
                s.setAbdomen(Double.parseDouble(this.abdomenField.getText()));
            }

            if (!hipsField.getText().isEmpty()) {
                s.setHips(Double.parseDouble(this.hipsField.getText()));
            }

            if (!backField.getText().isEmpty()) {
                s.setBack(Double.parseDouble(this.backField.getText()));
            }

            if (!bicepsField.getText().isEmpty()) {
                s.setBiceps(Double.parseDouble(this.bicepsField.getText()));
            }

            if (!forearmField.getText().isEmpty()) {
                s.setForearm(Double.parseDouble(this.forearmField.getText()));
            }

            if (!this.ageField.getText().isEmpty()) {
                s.setAge(Integer.valueOf(ageField.getText()));
            }

            if (this.classChooser.getSelectedIndex() > 0) {
                s.setClassName(this.classChooser.getSelectedItem().toString());
            }

            return s;
        }
    }
    
    //make sure there is a student name and that all the fields that are supposed to have numbers only have numbers

    private boolean validateFields(Component owner) {
        if (this.nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(owner, "The student name must not be empty.");
            return false;
        }
       
        if (!isInputNum(ageField,owner)) {
            return false;
        }
        
        if (!isInputNum(neckField,owner)) {
            return false;
        }
        
        if (!isInputNum(bustField,owner)) {
            return false;
        }
        
        if (!isInputNum(waistField,owner)) {
            return false;
        }
        
        if (!isInputNum(abdomenField,owner)) {
            return false;
        }
        
        if (!isInputNum(hipsField,owner)) {
            return false;
        }
        
        if (!isInputNum(backField,owner)) {
            return false;
        }
        
        if (!isInputNum(bicepsField,owner)) {
            return false;
        }
        
        if (!isInputNum(forearmField,owner)) {
            return false;
        }
        
        return true;
    }

    public boolean isInputNum(JTextField field, Component owner) {
        char[] nums = field.getText().toCharArray();
        int counter = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 48 && nums[i] <= 57 || nums[i] == 46) {
                counter++;
            }
        }

        if (counter != nums.length) {
            JOptionPane.showMessageDialog(owner, "The " + field.getName() + " must be a number.");
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
