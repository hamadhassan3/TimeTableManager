/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admtimetable;

import javafx.scene.control.Button;

/**
 *
 * @author hamad
 */
public class Slot {
    private String num;
    private String date;
    private String day;
    
    private String Event = "";
    private String Color = "";

    public String getEvent() {
        return Event;
    }

    public void setEvent(String Event) {
        this.Event = Event;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }
    
    
    
    
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }



    public Slot(String num, String date, String day) {
        this.num = num;
        this.date = date;
        this.day = day;
    }
    


 
    
    
}
