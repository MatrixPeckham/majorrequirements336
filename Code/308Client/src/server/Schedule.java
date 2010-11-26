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
        if(m==null) {return null;}
        RootlessTree<Course> t=m.getRemainingCourse(u.getRecords(), 2008);
        Vector<Course> toTake=new Vector<Course>();
        Vector<Course> toRemove=new Vector<Course>();
        int maxlevel=t.getMaxLevel();
        while(maxlevel>=0) {
            toTake.addAll(t.getDataAtlevel(maxlevel));
            maxlevel--;
        }
        //t=null;
        /*Semester s=Semester.freeSemester();
        TreeMap<String, Vector<Course>> sched=new TreeMap<String, Vector<Course>>();
        int maxSemCredits=School.getSchool().getMaxCreds();
        toTake=t.getDataAtlevel(maxlevel);
        sched.put(s.toString(), new Vector<Course>());
        while(t.count()>0) {
            boolean found=false;

            Iterator<Course> it=toTake.iterator();
            while(it.hasNext()) {

                Course c=it.next();
                if(c.canTake(s) && c.getCredits()<=maxSemCredits && !t.hasChildren(c)) {
                    sched.get(s.toString()).add(c);
                    toRemove.add(c);
                    it.remove();
                    maxSemCredits-=c.getCredits();
                    found=true;

                }
            }
            boolean reset=false;
            if((toTake.size()==0 || !found) && maxlevel>=0) {
                        maxlevel--;
                        reset=true;
            }
             if(maxlevel<0 || maxSemCredits==0) {
                    for(Course c : toRemove){
                        t.removeData(c);
                    }
                    toRemove.clear();
                    s=s.nextSemester();
                    sched.put(s.toString(), new Vector<Course>());
                    maxSemCredits=School.getSchool().getMaxCreds();
                    maxlevel=School.getSchool().getMaxCreds();
                    reset=true;
            }
            if(reset) {
                toTake=t.getDataAtlevel(maxlevel);
            }
            
           
        }*/
        boolean canGenerate=true;
        Semester s=Semester.freeSemester();
        int maxSemCredits=School.getSchool().getMaxCreds();
        TreeMap<String, Vector<Course>> sched=new TreeMap<String, Vector<Course>>();
        TreeMap<String, CourseRecord> added=new TreeMap<String,CourseRecord>();
        int totalCredits=u.getCompletedCredits();
        sched.put(s.toString(), new Vector<Course>());
        boolean flag=false;
        while(toTake.size()>0 && canGenerate) {
            Iterator<Course> it=toTake.iterator();
            boolean found=false;
            while(it.hasNext()) {
                Course c=it.next();
                if(c.canTake(s) && c.getCredits()<=maxSemCredits && c.prereqsComplete(added, totalCredits)) {
                    sched.get(s.toString()).add(c);
                    added.put(c.getId(), new CourseRecord(c, new Grade("A"), false));
                    toRemove.add(c);
                    totalCredits+=c.getCredits();
                    it.remove();
                    maxSemCredits-=c.getCredits();
                    found=true;
                }
                if(maxSemCredits==0) {
                    for(Course c2 : toRemove) {
                        t.removeData(c2);
                    }
                    toRemove.clear();
                    s=s.nextSemester();
                    sched.put(s.toString(), new Vector<Course>());
                    maxSemCredits=School.getSchool().getMaxCreds();
                }
            }
            if(maxSemCredits>0) {
                    for(Course c2 : toRemove) {
                        t.removeData(c2);
                    }
                    toRemove.clear();
                    s=s.nextSemester();
                    sched.put(s.toString(), new Vector<Course>());
                    maxSemCredits=School.getSchool().getMaxCreds();
                }
            if(!found) {
                if(flag) {break;}
                flag=true;
            } else {flag=false;}
        }
        return new Schedule(sched);
    }

}
