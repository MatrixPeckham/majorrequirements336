/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.Date;

/**
 * Class to handle offering of courses in years to come
 * @author Bill
 */
public class CourseOffering implements Serializable{
    Semester semester;
    boolean confirmed;
    public CourseOffering(Semester s, boolean b){
        semester=s;
        confirmed=b;
    }
    public CourseOffering(){
        Date d = new Date();
        semester = Semester.freeSemester();
        confirmed=false;
    }
    public Semester getSemester(){
        return semester;
    }
    public void setSemester(Semester s){
        semester=s;
    }
    public boolean isConfirmed(){
        return confirmed;
    }
    public void setConfirmed(boolean c){
        confirmed = c;
    }
}
