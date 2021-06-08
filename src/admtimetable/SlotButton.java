/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admtimetable;

import java.awt.Color;
import javafx.scene.control.Button;

/**
 *
 * @author hamad
 */
public class SlotButton {
    private Button but;
    private String color = null;
    private boolean isOff = false;
    private int num;

    private int numInsideBut;
    
    public int getNumInsideBut() {
        return numInsideBut;
    }

    public void setNumInsideBut(int numInsideBut) {
        this.numInsideBut = numInsideBut;
    }


    
    
    public boolean isIsOff() {
        return isOff;
    }

    public void setIsOff(boolean isOff) {
        this.isOff = isOff;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    

    public String getColor() {
        
        return color;
    }

    public void setColor(String color) {
        if(color!= null && color.equalsIgnoreCase("black")){
            this.isOff = true;
        }
        this.color = color;
    }

    public SlotButton(Button but) {
        this.but = but;
    }

    public Button getBut() {
        return but;
    }

    public void setBut(Button but) {
        this.but = but;
    }
    
    
}
