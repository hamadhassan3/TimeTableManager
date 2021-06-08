/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admtimetable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    public int blocksUsed = 0;
    public int transitionCount = 0;
    
    public int l1 = 0;
    public int l2 = 0;
    public int l3 = 0;
    public int l4 = 0;
    public int l5 = 0;
    public int l6 = 0;
    public int l7 = 0;
    public int l8 = 0;
    

    
    public static TimeTable tb = null;
    
    public static void setTimeTable(Calendar stDate, Calendar enDate, boolean sunTCheck, boolean satTCheck, boolean mon, boolean tue, boolean wed, boolean thur, boolean fri, boolean sat, boolean sun){
        tb = new TimeTable(stDate, enDate, sunTCheck, satTCheck, mon, tue, wed, thur, fri, sat, sun);
    }
    
    public List<SlotButton> lsb = new ArrayList<SlotButton>();
    public List<Slot> ls = new ArrayList<Slot>(); 
    
    private TimeTable(Calendar stDate, Calendar enDate, boolean sunTCheck, boolean satTCheck, boolean mon, boolean tue, boolean wed, boolean thur, boolean fri, boolean sat, boolean sun) {
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
