package bollysteps.database;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import bollysteps.database.danceclass.*;
import bollysteps.database.student.*;
import com.google.gson.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.*;

/**
 *
 * @author aayus
 */
public class Database {

    // the default name of the database file
    public static final String dbFileName = "danceClass.json";
    
    private final transient Logger logger;

    //fields to store classes and students - stored in the JSON file
    private final ConcurrentHashMap<String, DanceClass> classes;
    private final ConcurrentHashMap<String, Student> students;

    //fields to keep track of event listeners, but transient keyword prevents them from being stored in JSON
    private final transient List<DanceClassEventListener> danceClassEventListeners;
    private final transient List<StudentEventListener> studentEventListeners;

    public Database() {
        this.logger = Logger.getLogger("DB");

        this.classes = new ConcurrentHashMap<String, DanceClass>();
        this.students = new ConcurrentHashMap<String, Student>();

        this.danceClassEventListeners = new ArrayList<DanceClassEventListener>();
        this.studentEventListeners = new ArrayList<StudentEventListener>();
    }

    //methods to add and remove both danceClassEventListeners and studentEventListeners
    public synchronized void addEventListener(DanceClassEventListener listener) {
        this.danceClassEventListeners.add(listener);
    }

    public synchronized void removeEventListener(DanceClassEventListener listener) {
        this.danceClassEventListeners.remove(listener);
    }

    public synchronized void addEventListener(StudentEventListener listener) {
        this.studentEventListeners.add(listener);
    }

    public synchronized void removeEventListener(StudentEventListener listener) {
        this.studentEventListeners.remove(listener);
    }

    //if addStudent is called without boolean parameter, it is set to true by this method
    public synchronized void addStudent(Student s) {
        addStudent(s, true);
    }

    //adds student to HashMap if the student does not already exist, fires event if boolean is true 
    private synchronized void addStudent(Student s, boolean fireEvent) {
        if (students.containsKey(s.getName())) {
            throw new IllegalArgumentException("Sorry! This student already exists!");
        } else {
            this.students.put(s.getName(), s);
            if (fireEvent) {
                this.fireStudentAddedEvent(s);
            }
        }
    }

    //if deleteStudent is called without boolean parameter, it is set to true by this method
    public void deleteStudent(String studentName) {
        deleteStudent(studentName, true);
    }

    //removes student from HashMap, fires event if boolean is true
    private void deleteStudent(String studentName, boolean fireEvent) {
        Student s = students.get(studentName);
        if (s == null) {
            return;
        }

        students.remove(studentName);
        if (fireEvent) {
            this.fireStudentRemovedEvent(s);
        }
    }

    //gets the existing student and deletes them from Hashmap, adds new student to HashMap, fires event
    public synchronized void modifyStudent(Student newStudent) {
        Student existingStudent = students.get(newStudent.getName());
        deleteStudent(existingStudent.getName(), false);
        addStudent(newStudent, false);
        this.fireStudentModifiedEvent(existingStudent, newStudent);

    }

    //if addDanceClass is called without boolean parameter, it is set to true by this method
    public void addDanceClass(DanceClass dc) {
        this.addDanceClass(dc, true);
    }

    //adds dance class to HashMap if the class does not already exist, fires event if boolean is true 
    private void addDanceClass(DanceClass dc, boolean fireEvent) {
        if (classes.containsKey(dc.getName())) {
            throw new IllegalArgumentException("Sorry! This class already exists!");
        } else {
            this.classes.put(dc.getName(), dc);
            if (fireEvent) {
                this.fireDanceClassAddedEvent(dc);
            }
        }
    }

    //if deleteDanceClass is called without boolean parameter, it is set to true by this method
    public void deleteDanceClass(String className) {
        this.deleteDanceClass(className, true);
    }

    //removes class from HashMap, sets class name to null for students who were assigned to the class being deleted, fires event if boolean is true
    private void deleteDanceClass(String className, boolean fireEvent) {
        DanceClass c = classes.get(className);
        if (c == null) {
            return;
        }
        for (Student s : this.students.values()) {
            if (className.equals(s.getClassName())) {
                s.setClassName(null);

                if (fireEvent) {
                    fireStudentModifiedEvent(s, s);
                }
            }
        }

        classes.remove(className);

        if (fireEvent) {
            this.fireDanceClassRemovedEvent(c);
        }

    }

        //gets the existing class and deletes them from Hashmap, adds new class to HashMap, fires event
    public void modifyDanceClass(DanceClass newDC) {
        DanceClass existingDC = classes.get(newDC.getName());
        deleteDanceClass(existingDC.getName(), false);
        addDanceClass(newDC, false);
        this.fireDanceClassModifiedEvent(existingDC, newDC);
    }

    //returns all students if no classNameFilter is passed in
    public Collection<Student> getAllStudents() {
        return this.getAllStudents(null);
    }

    //returns all students in the class name passed in as the parameter
    public List<Student> getAllStudents(String classNameFilter) {

        if (classNameFilter != null) {
            List<Student> studentsInClass = new ArrayList<>();
            for (Student s : this.students.values()) {
                if (classNameFilter.equals(s.getClassName())) {
                    studentsInClass.add(s);
                }
            }

            return studentsInClass;
        } else {
            return new ArrayList<Student>(this.students.values());
        }
    }
    
//returns all dance classes
    public List<DanceClass> getAllClasses() {
        return new ArrayList(this.classes.values());
    }

    //clears students and classes HashMaps
    public void deleteAll() {
        students.clear();
        classes.clear();
    }

    // returns the Dabase object from the default JSON file 
    public static Database loadFrom() throws FileNotFoundException {
       return loadFrom(dbFileName);
    }
    
    //returns Database object from JSON file (file name passed as parameter)
    public static Database loadFrom(String fileName) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.fromJson(new FileReader(fileName), Database.class);
    }

     //converts Database object into default JSON file 
    public void saveTo() throws IOException {
        saveTo(dbFileName);
    }
    
    //converts Database object into JSON file (file name passed as parameter)
    public void saveTo(String fileName) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(this, writer);
        }
    }

    @Override
    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String dataText = gson.toJson(this);

        return dataText;
    }

    //these methods fire events for dance classes or students added, modified, or removed
    private synchronized void fireDanceClassAddedEvent(DanceClass danceClass) {
        DanceClassEvent danceClassEvent = new DanceClassEvent(this, danceClass);
        DanceClassEventListener[] listeners = this.danceClassEventListeners.toArray(new DanceClassEventListener[0]);
        for (DanceClassEventListener l : listeners) {
            l.danceClassAdded(danceClassEvent);
        }

    }

    private synchronized void fireDanceClassRemovedEvent(DanceClass danceClass) {
        DanceClassEvent danceClassEvent = new DanceClassEvent(this, danceClass);
        for (DanceClassEventListener l : this.danceClassEventListeners) {
            l.danceClassRemoved(danceClassEvent);
        }

    }

    private synchronized void fireDanceClassModifiedEvent(DanceClass existingDC, DanceClass newDC) {
        ModifiedDanceClassEvent danceClassEvent = new ModifiedDanceClassEvent(this, existingDC, newDC);
        for (DanceClassEventListener l : this.danceClassEventListeners) {
            l.danceClassModified(danceClassEvent);
        }

    }

    private synchronized void fireStudentAddedEvent(Student student) {
        StudentEvent studentEvent = new StudentEvent(this, student);
        for (StudentEventListener l : this.studentEventListeners) {
            l.studentAdded(studentEvent);
        }

    }

    private synchronized void fireStudentRemovedEvent(Student student) {
        StudentEvent studentEvent = new StudentEvent(this, student);
        for (StudentEventListener l : this.studentEventListeners) {
            l.studentRemoved(studentEvent);
        }

    }

    private synchronized void fireStudentModifiedEvent(Student existingStudent, Student newStudent) {
        ModifiedStudentEvent mStudentEvent = new ModifiedStudentEvent(this, existingStudent, newStudent);
        for (StudentEventListener l : this.studentEventListeners) {
            l.studentModified(mStudentEvent);
        }

    }
}
