/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.*;
/**
 * Class to handle a list of offerings, and the
 * way to handle a semester that isn't listed
 * @author Bill
 */
@Entity
public class OfferingList implements Serializable {
    public static final byte NONE = 0;
    public static final byte FALL = 16;
    public static final byte SPRI = 2;
    public static final byte WINT = 1;
    public static final byte SUM1 = 4;
    public static final byte SUM2 = 8;
    public static final byte ALL = 16+8+4+2+1;
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private ArrayList<CourseOffering> offerings;
    private byte notListedStratagy;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public OfferingList(){
        offerings = new ArrayList<CourseOffering>();
        notListedStratagy = 0;
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
    public void setOfferings(ArrayList<CourseOffering> b){
        offerings=b;
    }
    public int isOffered(Semester s){
        for(CourseOffering co : offerings){
            if(co.getSemester().equals(s)){
                return co.isConfirmed()?1:0;
            }
        }
        if((s.getSeason()&notListedStratagy)!=0)
            return 0;
        return -1;
    }
    public boolean isConfirmed(Semester s){
        for(CourseOffering co : offerings){
            if(co.getSemester().equals(s)){
                return co.isConfirmed();
            }
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
