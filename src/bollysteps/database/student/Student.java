package bollysteps.database.student;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//add email etc
/**
 *
 * @author aayus
 */
public class Student { //object to represent a student at Bollysteps Dance School

    private String name;
    private String className;
    private String email;
    private int age;
    private String parentName;
    private String parentEmail;
    private String phoneNum;
    private double neck;
    private double bust;
    private double waist;
    private double abdomen;
    private double hips;
    private double back;
    private double biceps;
    private double forearm;

    
    //getters and setters for fields 
    
    public Student(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return this.className;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public double getNeck() {
        return neck;
    }

    public void setNeck(double neck) {
        this.neck = neck;
    }

    public double getBust() {
        return bust;
    }

    public void setBust(double bust) {
        this.bust = bust;
    }

    public double getWaist() {
        return waist;
    }

    public void setWaist(double waist) {
        this.waist = waist;
    }

    public double getAbdomen() {
        return abdomen;
    }

    public void setAbdomen(double abdomen) {
        this.abdomen = abdomen;
    }

    public double getHips() {
        return hips;
    }

    public void setHips(double hips) {
        this.hips = hips;
    }

    public double getBack() {
        return back;
    }

    public void setBack(double back) {
        this.back = back;
    }

    public double getBiceps() {
        return biceps;
    }

    public void setBiceps(double biceps) {
        this.biceps = biceps;
    }

    public double getForearm() {
        return forearm;
    }

    public void setForearm(double forearm) {
        this.forearm = forearm;
    }
}