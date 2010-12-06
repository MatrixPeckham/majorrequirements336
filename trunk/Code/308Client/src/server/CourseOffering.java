/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.persistence.Id;
import persistence.Updater;

/**
 * Class to handle offering of courses in years to come
 * @author Bill
 */
@Entity
public class CourseOffering implements Serializable, Updater{
    @OneToOne(cascade=CascadeType.ALL)
    private Semester semester;
    private boolean confirmed;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public String toString(){
        String s = "";
        if(confirmed){
            s+="is offered";
        } else {
            s+="is tentatively offered";
        }
        return s+" in "+semester.toString();
    }

    @Override
    public void update(Object toUpdate) {
        CourseOffering set=(CourseOffering)toUpdate;
        set.setSemester(semester);
        set.setConfirmed(confirmed);
    }
}
