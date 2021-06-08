/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admtimetable;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author hamad
 */
public class TimeTable {
    
    //This class will act as a struct
    public Calendar stDate;
    public Calendar enDate;
    public boolean sunTCheck;
    public boolean satTCheck;

    public boolean mon;
    public boolean tue;
    public boolean wed;
    public boolean thur;
    public boolean fri;
    public boolean sat;
    public boolean sun;    

    public TimeTable(Calendar stDate, Calendar enDate, boolean sunTCheck, boolean satTCheck, boolean mon, boolean tue, boolean wed, boolean thur, boolean fri, boolean sat, boolean sun) {
        this.stDate = stDate;
        this.enDate = enDate;
        this.sunTCheck = sunTCheck;
        this.satTCheck = satTCheck;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thur = thur;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        
    }
    
    

    
}
