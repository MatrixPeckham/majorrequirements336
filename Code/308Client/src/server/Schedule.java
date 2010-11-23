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

    public Schedule(TreeMap<String,Vector<Course>> s) {
        schedule=s;
    }
    public TreeMap<String,Vector<Course>> getSchedule() {
        return schedule;
    }
    public static Schedule generateSchedule(User u) {
        Major m=u.getMajor();
        RootlessTree<Course> t=m.getRemainingCourse(u.getRecords(), u.getMajorYear());
        Vector<Course> toTake=new Vector<Course>();
        int maxlevel=t.getMaxLevel();
        while(maxlevel>=0) {
            toTake.addAll(t.getDataAtlevel(maxlevel));
            maxlevel--;
        }
        boolean canGenerate=true;
        Semester s=Semester.freeSemester();
        int maxSemCredits=School.getSchool().getMaxCreds();
        TreeMap<String, Vector<Course>> sched=new TreeMap<String, Vector<Course>>();
        sched.put(s.toString(), new Vector<Course>());
        while(toTake.size()>0 && canGenerate) {
            Iterator<Course> it=toTake.iterator();
            boolean found=false;
            while(it.hasNext() && !found) {
                Course c=it.next();
                if(c.canTake(s) && c.getCredits()<=maxSemCredits) {
                    sched.get(s.toString()).add(c);
                    it.remove();
                    maxSemCredits-=c.getCredits();
                    found=true;
                }
            }
            if(maxSemCredits==0) {
                    s=s.nextSemester();
                    sched.put(s.toString(), new Vector<Course>());
            }
        }
        return new Schedule(sched);
    }

}
