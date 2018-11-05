package bollysteps.database.danceclass;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author aayus
 */
public class DanceClass {  //object to represent a dance class at Bollysteps Dance School
    private String name;
    private String startTime;
    private String endTime;
       
    public DanceClass(String name) {
        this.name = name;
    }
    
    //getters and setters for the different fields 
    public String getName() {
        return name;
    }
    
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    public String getStartTime() {
       return startTime;
    }
    
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    public String getEndTime() {
        return endTime;
    }
}
