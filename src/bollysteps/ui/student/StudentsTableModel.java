/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bollysteps.ui.student;

import bollysteps.database.*;
import bollysteps.database.student.*;
import javax.swing.table.AbstractTableModel;
import java.util.*;

/**
 *
 * @author aayus
 */

//AbstractTableModel used to create the students JTable

public class StudentsTableModel extends AbstractTableModel implements StudentEventListener {

    private final Database db;
    private final String classNameFilter;
    private final ArrayList<Student> students;
    private String[] columnNames;

    //overloading the constructor to create different columns depending on whether a classNameFilter is given
    
    public StudentsTableModel(Database db) {
        this(db, null);
        columnNames = new String[]{
            "Name",
            "Age",
            "Dance Class"
        };
       
    }

    public StudentsTableModel(Database db, String classNameFilter) {
        this.db = db;
        this.classNameFilter = classNameFilter;
        this.db.addEventListener(this);
        this.students = new ArrayList<Student>(this.db.getAllStudents(classNameFilter));
       
        
        columnNames = new String[]{
            "Name",
            "Age",
        };
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getColumnCount() {

        return columnNames.length;
    }

    @Override
    public int getRowCount() {

        return this.students.size();
    }

    @Override
    public Object getValueAt(int row, int col) {

        Student s = this.students.get(row);
        if (col == 0) {
            return s.getName();
        } else if (col == 1) {
            return s.getAge();
        } else if (col == 2) {
            return s.getClassName();
        } else {
            return null;
        }
    }

    //methods implemented from StudentEventListener
    
    @Override
    public void studentAdded(StudentEvent event) {

        Student s = event.getStudent();
        //only adds class is the filter is null(so all students are being considered)
        //or if the student's class name matches the filter
        
        if ((this.classNameFilter == null) || (this.classNameFilter.equals(s.getClassName()))) {
            this.students.add(event.getStudent());
            int index = this.students.size() - 1;
            this.fireTableRowsInserted(index, index);
        }
    }

    @Override
    public void studentRemoved(StudentEvent event) {
        int index = this.students.indexOf(event.getStudent());
        if (index != -1) {
            this.students.remove(index);
            this.fireTableRowsDeleted(index, index);
        }
    }

    @Override
    public void studentModified(ModifiedStudentEvent event) {
        int foundIndex = this.students.indexOf(event.getStudent());

        if (foundIndex != -1) { //if the student is one that was being tracked in the model before the modify event
            Student newStudent = event.getNewStudent();
            if ((this.classNameFilter == null) //if the filter is null or matches the student's class name 
                    || (this.classNameFilter.equals(newStudent.getClassName()))) { //(meaning the student is relevant to the model)
                this.students.remove(foundIndex); //then remove the old student
                this.students.add(foundIndex, newStudent); //and replace with the new student 
                this.fireTableRowsUpdated(foundIndex, foundIndex); //notify the table that rows need to be updated
            } else { //if the student is not relevant to the model anymore (student's class name does not match filter)
                this.students.remove(foundIndex); //remove the student 
                this.fireTableRowsDeleted(foundIndex, foundIndex); //notify the table that rows need to be deleted 
            }
        } else { //if the student was not being tracked in the model before (so it's class name didn't match the filter) 
            Student newStudent = event.getNewStudent();
            if ((this.classNameFilter == null) //if the filter is null or matches the student's class name 
                    || (this.classNameFilter.equals(newStudent.getClassName()))) { //(meaning the student is relevant to the model)
                this.students.add(newStudent); //add the student
                int addedIndex = this.students.size() - 1;
                this.fireTableRowsInserted(addedIndex, addedIndex); //notify the table that rows need to be inserted 
            }
        }
    }

    public Student getStudent(int row) {
        return students.get(row);
    }
}
