/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import java.io.Serializable;
import java.util.*;
import structures.RootlessTree;
/**
 *
 * @author Bill
 */
public class Schedule implements Serializable{
    private TreeMap<String,Vector<Course>>  schedule;

    public static Schedule generateSchedule(User u) {
        Major m=u.getMajor();
        RootlessTree<Course> t=m.getRemainingCourse(u.getRecords(), u.getMajorYear());
        while(t.count()>0) {
            
        }
        return new Schedule();
    }
}
