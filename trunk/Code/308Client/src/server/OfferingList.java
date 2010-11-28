/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to handle a list of offerings, and the
 * way to handle a semester that isn't listed
 * @author Bill
 */
public class OfferingList implements Serializable {
    public static final byte NONE = 0;
    public static final byte FALL = 1;
    public static final byte SPRI = 2;
    public static final byte WINT = 4;
    public static final byte SUM1 = 8;
    public static final byte SUM2 = 16;
    public static final byte ALL = (byte) 0xFF;
    private ArrayList<CourseOffering> offerings;
    private byte notListedStratagy;
    public OfferingList(){
        offerings = new ArrayList<CourseOffering>();
        notListedStratagy = ALL;
    }
    public ArrayList<CourseOffering> getOfferings(){
        return offerings;
    }
    public void addOffering(Semester s, boolean c){
        offerings.add(new CourseOffering(s,c));
    }
    public byte getNotListedStratagy(){
        return notListedStratagy;
    }
    public void setNotListedStratagy(byte b){
        notListedStratagy=b;
    }
    public boolean isOffered(Semester s){
        for(CourseOffering co : offerings){
            if(co.getSemester().equals(s)){
                return true;
            }
        }
        if((s.getSeason()&notListedStratagy)!=0)
            return true;
        return false;
    }
    public boolean isConfirmed(Semester s){
        for(CourseOffering co : offerings){
            if(co.getSemester().equals(s)){
                return co.isConfirmed();
            }
        }
        return false;
    }
}
